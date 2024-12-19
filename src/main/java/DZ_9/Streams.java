package DZ_9;

import java.util.ArrayList;
import java.util.List;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;

/**
 * Класс Streams предоставляет функциональный интерфейс для работы с коллекциями.
 * Позволяет применять фильтрацию, преобразование элементов и преобразование в Map
 * без изменения исходной коллекции.
 *
 * @param <T> Тип элементов в коллекции
 */
public class Streams<T> {
    private final List<T> collection;

    private Streams(List<T> collection) {
        this.collection = new ArrayList<>(collection); // Создаем копию, чтобы не менять исходную коллекцию
    }

    /**
     * Создает новый объект Streams на основе переданной коллекции.
     *
     * @param list Коллекция, из которой будет создан Streams
     * @param <T>  Тип элементов в коллекции
     * @return Новый объект Streams
     */
    public static <T> Streams<T> of(List<? extends T> list) {
        return (Streams<T>) new Streams<>(list);
    }

    /**
     * Оставляет в коллекции только те элементы, которые удовлетворяют указанному условию.
     *
     * @param predicate Лямбда-выражение или объект Predicate, определяющий условие фильтрации
     * @return Новый объект Streams с отфильтрованными элементами
     */
    public Streams<T> filter(Predicate<? super T> predicate) {
        Objects.requireNonNull(predicate);
        List<T> filtered = collection.stream()
                .filter(predicate)
                .collect(Collectors.toList());
        return new Streams<>(filtered);
    }

    /**
     * Преобразует элементы коллекции в другой тип с использованием переданной функции.
     *
     * @param transformer Функция, преобразующая элементы исходной коллекции
     * @param <R>         Тип элементов после преобразования
     * @return Новый объект Streams с элементами нового типа
     */
    public <R> Streams<R> transform(Function<? super T, ? extends R> transformer) {
        Objects.requireNonNull(transformer);
        List<R> transformed = collection.stream()
                .map(transformer)
                .collect(Collectors.toList());
        return new Streams<>(transformed);
    }

    /**
     * Преобразует элементы коллекции в Map на основе двух функций:
     * одна определяет ключ, другая значение.
     *
     * @param keyMapper   Функция для получения ключей из элементов коллекции
     * @param valueMapper Функция для получения значений из элементов коллекции
     * @param <K>         Тип ключей в результирующей карте
     * @param <V>         Тип значений в результирующей карте
     * @return Map, содержащий ключи и значения, определенные переданными функциями
     * @throws IllegalStateException если в коллекции есть дублирующиеся ключи
     */
    public <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper, Function<? super T, ? extends V> valueMapper) {
        Objects.requireNonNull(keyMapper);
        Objects.requireNonNull(valueMapper);
        return collection.stream()
                .collect(Collectors.toMap(keyMapper, valueMapper, (existing, replacement) -> existing));
    }
}

