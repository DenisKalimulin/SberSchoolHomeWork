package DZ_13;

/**
 * Перечисление, описывающее доступные типы кеша.
 */
public enum CacheType {
    IN_MEMORY,  // в памяти в течении работы приложения
    FILE  // кеширование в файловую систему для сохранения между перезапусками приложения
}