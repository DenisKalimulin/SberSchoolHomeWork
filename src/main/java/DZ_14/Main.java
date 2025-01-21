package DZ_14;

public class Main {
    public static void main(String[] args) {
        Calculator calculator = new Calculator();

        // Вычисление и сохранение в кэш (БД)
        System.out.println(calculator.fibonachi(10));

        // Берем результат из кэша
        System.out.println(calculator.fibonachi(10));

        // Проверка для числа 5
        System.out.println(calculator.fibonachi(5));
    }
}


