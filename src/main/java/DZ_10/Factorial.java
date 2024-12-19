package DZ_10;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.math.BigInteger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Factorial {
    private static final String FILE_PATH = "/Users/deniskalimulin/IdeaProjects/Test/SberSchoolHomeWork/src/main/java/DZ_10/numbers.txt";
    private static ExecutorService executorService;

    /**
     * Метод для вычисления факториала
     * @param n число для которого необходимо рассчитать факториал
     * @return факториал числа n
     */
    public static BigInteger factorial(int n) {
        BigInteger result = BigInteger.ONE;
        for (int i = 2; i <= n; i++) {
            result = result.multiply(BigInteger.valueOf(i));
        }
        return result;
    }

    public static void main(String[] args) {
        AddNumbers addNumbers = new AddNumbers(FILE_PATH);
        addNumbers.add(); // Заполняем файл случайными значениями от 1 до 50

        executorService = Executors.newFixedThreadPool(10);

        try(BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                int num = Integer.parseInt(line);

                Runnable task = () -> {
                    BigInteger result = factorial(num);
                    System.out.println("Факториал числа " + num + " = " + result);
                };

                executorService.submit(task);
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }

        executorService.shutdown();

        try {
            if(!executorService.awaitTermination(60, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
        }

    }

}
