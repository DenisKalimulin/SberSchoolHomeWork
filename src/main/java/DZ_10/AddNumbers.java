package DZ_10;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

/**
 * Класс для заполнения number.txt пятью рандомными значениями от 1 до 50
 */
public class AddNumbers {
    private String filePath;
    private static Random random = new Random();

    public AddNumbers(String filePath) {
        this.filePath = filePath;
    }

    public void add() {
        try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath, true))){
            for (int i = 0; i < 5; i++) {
                int j = random.nextInt(50);
                writer.write(String.valueOf(j));
                writer.newLine();
            }
        } catch (IOException e) {
            System.err.println("Ошибка записи: " + e.getMessage());
        }
    }
}
