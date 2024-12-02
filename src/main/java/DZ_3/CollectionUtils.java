package DZ_3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class CollectionUtils {
    /**
     * Добавляет все элементы из источника в назначение
     *
     * @param source      список, из которого будут добавлены элементы (производитель)
     * @param destination список, в который будут добавлены элементы (потребитель)
     * @param <T>         тип элементов
     */
    public static <T> void addAll(List<? extends T> source, List<? super T> destination) {
        destination.addAll(source);
        
    }

    /**
     * Создает новый пустой список
     *
     * @param <T> тип элементов списка
     * @return новый пустой список
     */
    public static <T> List<T> newArrayList() {
        return new ArrayList<>();
    }

    /**
     * Находит индекс указанного элемента в списке
     *
     * @param source список, в котором будет выполнен поиск
     * @param o      элемент, индекс которого нужно найти
     * @return индекс элемента в списке или -1, если элемент не найден
     */
    public static int indexOf(List<?> source, Object o) {
        return source.indexOf(o);
    }

    /**
     * Ограничивает размер списка до указанного размера
     *
     * @param source исходный список
     * @param size   максимальный размер
     * @return подсписок, содержащий элементы до указанного размера
     */
    public static List<?> limit(List<?> source, int size) {
        return source.subList(0, Math.min(size, source.size()));
    }

    /**
     * Добавляет элемент в указанный список
     *
     * @param source список, в который будет добавлен элемент
     * @param o      элемент, который нужно добавить
     */
    public static void add(List<Object> source, Object o) {
        source.add(o);
    }

    /**
     * Удаляет все элементы из одного списка, которые содержатся в другом списке
     *
     * @param removeFrom список, из которого будут удалены элементы
     * @param c2         список, содержащий элементы для удаления
     */
    public static void removeAll(List<Object> removeFrom, List<?> c2) {
        removeFrom.removeAll(c2);
    }

    /**
     * Проверяет, содержит ли первый список все элементы второго списка
     *
     * @param c1 первый список
     * @param c2 второй список
     * @return true, если первый список содержит все элементы второго; иначе false
     */
    public static boolean containsAll(List<?> c1, List<?> c2) {
        return c1.containsAll(c2);
    }

    /**
     * Проверяет, содержит ли первый список хотя бы один элемент из второго списка
     *
     * @param c1 первый список
     * @param c2 второй список
     * @return true, если первый список содержит хотя бы один элемент второго; иначе false
     */
    public static boolean containsAny(List<?> c1, List<?> c2) {
        for (Object item : c2) {
            if (c1.contains(item)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает список элементов из входного списка, которые находятся в заданном диапазоне
     *
     * @param list входной список
     * @param min  минимальное значение диапазона (включительно)
     * @param max  максимальное значение диапазона (включительно)
     * @return новый список, содержащий элементы в заданном диапазоне
     */
    public static List<Object> range(List<?> list, Object min, Object max) {
        List<Object> result = new ArrayList<>();
        for (Object item : list) {
            if (item instanceof Comparable) {
                Comparable comparableItem = (Comparable) item;
                if (comparableItem.compareTo(min) >= 0 && comparableItem.compareTo(max) <= 0) {
                    result.add(item);
                }
            }
        }
        return result;
    }

    /**
     * Возвращает список элементов из входного списка, которые находятся в заданном диапазоне,
     * с использованием указанного компаратора
     *
     * @param list       входной список
     * @param min        минимальное значение диапазона (включительно)
     * @param max        максимальное значение диапазона (включительно)
     * @param comparator компаратор для сравнения элементов
     * @return новый список, содержащий элементы в заданном диапазоне
     */
    public static List<Object> range(List<?> list, Object min, Object max, Comparator<Object> comparator) {
        List<Object> result = new ArrayList<>();
        for (Object item : list) {
            if (comparator.compare(item, min) >= 0 && comparator.compare(item, max) <= 0) {
                result.add(item);
            }
        }
        return result;
    }
}

