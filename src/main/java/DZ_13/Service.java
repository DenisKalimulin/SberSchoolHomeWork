package DZ_13;

import DZ_8.Cache;
import DZ_8.CacheType;

import java.util.List;

/**
 * Интерфейс сервиса, содержащий методы для выполнения длительных операций
 * с возможностью кеширования их результатов.
 */
public interface Service {
    @DZ_8.Cache(cacheType = DZ_8.CacheType.FILE, fileNamePrefix = "data", zip = true, identityBy = {String.class})
    double doHardWork(String item, double value);

    @Cache(cacheType = CacheType.IN_MEMORY, listLimit = 100_000)
    List<String> getData(String param);
}
