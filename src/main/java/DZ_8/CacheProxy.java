package DZ_8;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

/**
 * Класс CacheProxy реализует функциональность кеширования вызовов методов через динамические прокси.
 * Позволяет сохранять результаты методов в оперативной памяти или на диске с возможностью сжатия данных.
 */
public class CacheProxy {
    private final File rootDir;
    private final Map<String, Object> inMemoryCache = new HashMap<>();

    public CacheProxy(File rootDir) {
        this.rootDir = rootDir;
        if (!rootDir.exists()) {
            rootDir.mkdirs();
        }
    }

    /**
     * Создает прокси-объект для заданного целевого объекта, добавляя функциональность кеширования.
     *
     * @param target целевой объект, методы которого будут обрабатываться с использованием кеша.
     * @param <T>    тип целевого объекта.
     * @return прокси-объект, реализующий кеширование.
     */
    @SuppressWarnings("unchecked")
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
                            if (inMemoryCache.containsKey(cacheKey)) {
                                System.out.println("Returning from in-memory cache: " + cacheKey);
                                return inMemoryCache.get(cacheKey);
                            }
                            Object result = method.invoke(target, args);
                            if (result instanceof List<?> listResult) {
                                result = limitListSize(listResult, cacheAnnotation.listLimit());
                            }
                            inMemoryCache.put(cacheKey, result);
                            return result;
                        }
                        case FILE -> {
                            File cacheFile = new File(rootDir, cacheKey + ".cache");
                            if (cacheFile.exists()) {
                                System.out.println("Returning from file cache: " + cacheFile.getName());
                                return readFromFile(cacheFile, cacheAnnotation.zip());
                            }
                            Object result = method.invoke(target, args);
                            if (result instanceof List<?> listResult) {
                                result = limitListSize(listResult, cacheAnnotation.listLimit());
                            }
                            saveToFile(cacheFile, result, cacheAnnotation.zip());
                            return result;
                        }
                        default -> throw new IllegalStateException("Unknown cache type");
                    }
                });
    }

    /**
     * Генерирует ключ для кеша на основе метода, аргументов и аннотации {@link Cache}.
     *
     * @param method           метод, для которого создается ключ.
     * @param args             аргументы вызова метода.
     * @param cacheAnnotation  аннотация {@link Cache}, содержащая параметры кеширования.
     * @return строка, представляющая уникальный ключ для кеша.
     */
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

    /**
     * Ограничивает размер списка, если он превышает заданный лимит.
     *
     * @param list  Исходный список.
     * @param limit Лимит количества элементов в возвращаемом списке.
     * @return Ограниченный список.
     */
    private List<?> limitListSize(List<?> list, int limit) {
        if (list.size() > limit) {
            System.out.println("Limiting list size to " + limit);
            return list.subList(0, limit);
        }
        return list;
    }

    /**
     * Сохраняет данные в файл, с возможностью сжатия.
     *
     * @param file Файл, в который будут записаны данные.
     * @param data Данные для записи.
     * @param zip  Использовать ли сжатие.
     * @throws IOException Если произошла ошибка ввода/вывода.
     */
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

    /**
     * Считывает данные из файла с возможностью разархивации.
     *
     * @param file Файл, из которого будут считаны данные.
     * @param zip  Указывает, следует ли использовать разархивацию при чтении.
     *             Если {@code true}, данные будут извлекаться из ZIP-архива.
     * @return Объект, прочитанный из файла.
     * @throws IOException            Если произошла ошибка ввода/вывода при чтении файла.
     * @throws ClassNotFoundException Если класс объекта, хранящегося в файле, не может быть найден.
     */
private Object readFromFile(File file, boolean zip) throws IOException, ClassNotFoundException {
        try (FileInputStream fis = new FileInputStream(file);
             InputStream is = zip ? new ZipInputStream(fis) {{
                 getNextEntry();
             }} : fis;
             ObjectInputStream ois = new ObjectInputStream(is)) {
            return ois.readObject();
        }
    }
}
