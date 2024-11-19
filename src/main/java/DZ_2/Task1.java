package DZ_2;

import java.util.HashMap;
import java.util.HashSet;

public class Task1 {
    // Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся).
    public static final String[] words = {"Java", "Hello", "Python", "Yellow",
            "PHP", "Android", "Apple", "Game", "Phone",
            "Java", "Animal", "Cat", "Cat",
            "Dog", "Bird", "Black", "Java", "Pink", "Black"};

    // Найти и вывести список уникальных слов, из которых состоит массив (Дубликаты не считаем)

    /**
     * Выводит список уникальных слов из переданного массива строк
     * <p/>Метод принимает массив строк и добавляет каждое слово в HashSet,
     * благодаря чему избегает повтора элементов.
     * После этого метод выводит уникальные слова на экран.
     *
     * @param words массив строк, из которого необходимо извлечь уникальные слова
     */
    public static void uniqueWords(String[] words) {
        HashSet<String> uniqueWords = new HashSet<>();
        for (String word : words) {
            uniqueWords.add(word);
        }

        System.out.println("Список уникальных слов из массива: ");

        for (String word : uniqueWords) {
            System.out.println(word);
        }
    }

    //Посчитать сколько раз встречается каждое слово\

    /**
     * Метод выводит уникальные слова из массива строк и сколько раз они там встречаются
     * <p>
     * <p/> Метод принимает массив строк,
     * создает HashMap(ключ - это слово, значение - количество совпадений в массиве).
     * Выводит результат на экран
     *
     * @param words массив строк, в котором необходимо подсчитать количество повторений каждого слова
     */
    public static void counterWords(String[] words) {
        HashMap<String, Integer> wordAndAmount = new HashMap<>();

        for (String word : words) {
            word = word.toLowerCase();

            wordAndAmount.put(word, wordAndAmount.getOrDefault(word, 0) + 1);
        }

        System.out.println("Слово + его количество:");

        for (HashMap.Entry<String, Integer> entry : wordAndAmount.entrySet()) {
            System.out.println(entry.getKey() + " : " + entry.getValue());
        }
    }
}

