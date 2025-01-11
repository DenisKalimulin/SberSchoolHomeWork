package DZ_13;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        CacheProxy cacheProxy = new CacheProxy(new File("cache"));

        Service service = (Service) cacheProxy.cache(new ServiceImpl());

        System.out.println(service.doHardWork("work1", 10));
        System.out.println(service.doHardWork("work1", 10));

        System.out.println(service.getData("param").size());
    }

}
