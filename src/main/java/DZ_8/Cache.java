package DZ_8;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для настройки поведения кеширования на уровне метода.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface Cache {
    /**
     * Определяет тип кеша: в памяти или на диске.
     *
     * @return тип кеша
     */
    CacheType cacheType() default CacheType.IN_MEMORY;

    /**
     * Префикс для имени файла при использовании кеширования на диске.
     * Если не задан, используется имя метода.
     *
     * @return префикс имени файла
     */
    String fileNamePrefix() default "";

    /**
     * Указывает, нужно ли сжимать кешированный файл в ZIP-архив.
     *
     * @return true, если сжатие включено; false, если сжатие не используется
     */
    boolean zip() default false;

    /**
     * Задает параметры метода, которые будут использоваться для определения уникальности кеша.
     * По умолчанию учитываются все аргументы метода.
     *
     * @return массив классов, которые определяют уникальность кеша
     */
    Class<?>[] identityBy() default {};

    /**
     * Максимальный размер списка, который будет храниться в кеше.
     * Для возвращаемых значений, не являющихся списками, это значение игнорируется.
     *
     * @return максимальное количество элементов в списке
     */
    int listLimit() default Integer.MAX_VALUE;
}