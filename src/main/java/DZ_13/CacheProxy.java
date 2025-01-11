package DZ_13;

import java.io.*;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class CacheProxy {
    private final File rootDir;
    private final Map<String, Object> inMemoryCache = new ConcurrentHashMap<>();
    private final Map<String, ReentrantLock> locks = new ConcurrentHashMap<>(); // Для синхронизации доступа к отдельным элементам

    public CacheProxy(File rootDir) {
        this.rootDir = rootDir;
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }
    }

    public <T> T cache(T target) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                (proxy, method, args) -> {
                    Cache cacheAnnotation = method.getAnnotation(Cache.class);
                    if (cacheAnnotation == null) {
                        return method.invoke(target, args);
                    }

                    String cacheKey = buildCacheKey(method, args, cacheAnnotation);

                    switch (cacheAnnotation.cacheType()) {
                        case IN_MEMORY -> {
                            return handleInMemoryCache(target, method, args, cacheKey, cacheAnnotation);
                        }
                        case FILE -> {
                            return handleFileCache(target, method, args, cacheKey, cacheAnnotation);
                        }
                        default -> throw new IllegalStateException("Unknown cache type");
                    }
                });
    }

    private String buildCacheKey(Method method, Object[] args, Cache cacheAnnotation) {
        StringBuilder keyBuilder = new StringBuilder(
                cacheAnnotation.fileNamePrefix().isEmpty() ? method.getName() : cacheAnnotation.fileNamePrefix());

        Set<Class<?>> identityBy = new HashSet<>(Arrays.asList(cacheAnnotation.identityBy()));
        if (identityBy.isEmpty()) {
            identityBy.addAll(Arrays.asList(method.getParameterTypes()));
        }

        for (int i = 0; i < args.length; i++) {
            if (identityBy.contains(method.getParameterTypes()[i])) {
                keyBuilder.append("_").append(args[i].toString());
            }
        }
        return keyBuilder.toString();
    }

    private List<?> limitListSize(List<?> list, int limit) {
        if (list.size() > limit) {
            System.out.println("Limiting list size to " + limit);
            return list.subList(0, limit);
        }
        return list;
    }

    private void saveToFile(File file, Object data, boolean zip) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            OutputStream os = zip ? new ZipOutputStream(fos) {{
                putNextEntry(new ZipEntry(file.getName()));
            }} : fos;

            try (ObjectOutputStream oos = new ObjectOutputStream(os)) {
                oos.writeObject(data);
            }
            if (zip) ((ZipOutputStream) os).closeEntry();
        }
    }

    private Object readFromFile(File file, boolean zip) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(file);
             InputStream is = zip ? new ZipInputStream(fis) {{
                 getNextEntry();
             }} : fis;
             ObjectInputStream ois = new ObjectInputStream(is)) {
            return ois.readObject();
        }
    }
    private Object handleInMemoryCache(Object target, Method method, Object[] args, String cacheKey, Cache cacheAnnotation) throws Throwable {
        // Получаем или создаем новый лок для элемента
        ReentrantLock lock = locks.computeIfAbsent(cacheKey, k -> new ReentrantLock());
        lock.lock();
        try {
            if (inMemoryCache.containsKey(cacheKey)) {
                System.out.println("Returning from in-memory cache: " + cacheKey);
                return inMemoryCache.get(cacheKey);
            }
            Object result = method.invoke(target, args);
            result = processCacheResult(result, cacheAnnotation);
            inMemoryCache.put(cacheKey, result);
            return result;
        } finally {
            lock.unlock();
        }
    }
    private Object handleFileCache(Object target, Method method, Object[] args, String cacheKey, Cache cacheAnnotation) throws Throwable {
        File cacheFile = new File(rootDir, cacheKey + ".cache");
        if (cacheFile.exists()) {
            System.out.println("Returning from file cache: " + cacheFile.getName());
            return readFromFile(cacheFile, cacheAnnotation.zip());
        }
        Object result = method.invoke(target, args);
        result = processCacheResult(result, cacheAnnotation);
        saveToFile(cacheFile, result, cacheAnnotation.zip());
        return result;
    }

    private Object processCacheResult(Object result, Cache cacheAnnotation) {
        if (result instanceof List<?> listResult) {
            return limitListSize(listResult, cacheAnnotation.listLimit());
        }
        return result;
    }
}

