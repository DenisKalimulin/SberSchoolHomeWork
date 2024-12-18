package DZ_8;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        CacheProxy cacheProxy = new CacheProxy(new File("cache"));
        Service service = cacheProxy.cache(new ServiceImpl());

        System.out.println(service.doHardWork("work1", 10)); // Вычисляется
        System.out.println(service.doHardWork("work1", 10)); // Из кеша

        System.out.println(service.getData("param").size()); // Ограниченный список
    }
}
