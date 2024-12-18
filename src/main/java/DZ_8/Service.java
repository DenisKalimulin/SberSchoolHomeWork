package DZ_8;

import java.util.List;

/**
 * Интерфейс сервиса, содержащий методы для выполнения длительных операций
 * с возможностью кеширования их результатов.
 */
public interface Service {
    @Cache(cacheType = CacheType.FILE, fileNamePrefix = "data", zip = true, identityBy = {String.class})
    double doHardWork(String item, double value);

    @Cache(cacheType = CacheType.IN_MEMORY, listLimit = 100_000)
    List<String> getData(String param);
}
