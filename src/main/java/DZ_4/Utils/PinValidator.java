package DZ_4.Utils;


import DZ_4.Exceptions.AccountIsLockedException;

import java.util.Scanner;

public class PinValidator {
    private static final int MAX_ATTEMPTS = 3;
    private static final long LOCK_TIME = 10000; // 10 секунд
    private int attemptCount = 0;
    private long lockStartTime = 0;
    private boolean isLocked = false;



    public boolean isValidPin(String pin) {
        final int MAX_ATTEMPTS = 3;          // Максимальное количество попыток
        final long LOCK_TIME = 10000;        // Время блокировки (в миллисекундах)

        long lockStartTime = 0;              // Время начала блокировки
        boolean isLocked = false;            // Флаг блокировки
        int attemptCount = 0;                // Счётчик попыток
        Scanner scanner = new Scanner(System.in);

        while (true) {
            // Проверяем, заблокирован ли аккаунт
            if (isLocked) {
                long remainingTime = LOCK_TIME - (System.currentTimeMillis() - lockStartTime);
                if (remainingTime > 0) {
                    System.err.println("Аккаунт заблокирован. Попробуйте через " + (remainingTime / 1000) + " секунд.");
                    try {
                        Thread.sleep(1000); // Подождем 1 секунду перед повторной проверкой
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt(); // Восстанавливаем статус прерывания
                    }
                    continue;
                } else {
                    // Снимаем блокировку, если время истекло
                    isLocked = false;
                    attemptCount = 0;
                    System.out.println("Аккаунт разблокирован. Можете попробовать снова.");
                }
            }

            // Ввод PIN-кода
            StringBuilder pinBuilder = new StringBuilder();
            while (pinBuilder.length() < 4) {
                System.out.println("Введите цифру пин-кода:");
                String num = scanner.nextLine();

                // Проверяем, что введён один символ и он является цифрой
                if (num.length() != 1 || !Character.isDigit(num.charAt(0))) {
                    System.err.println("Предупреждение: Введен нецифровой символ. Попробуйте снова.");
                    continue;
                }

                pinBuilder.append(num);

                // Проверяем PIN-код, когда длина достигла 4
                if (pinBuilder.length() == 4) {
                    if (pinBuilder.toString().equals(pin)) {
                        System.out.println("Пин-код верный!");
                        return true;
                    } else {
                        System.err.println("Пин-код неверный. Повторите попытку.");
                        attemptCount++;

                        // Проверяем, превышено ли количество попыток
                        if (attemptCount >= MAX_ATTEMPTS) {
                            isLocked = true;
                            lockStartTime = System.currentTimeMillis();
                            System.err.println("Счет заблокирован на 10 секунд.");
                            break; // Выходим из внутреннего цикла, чтобы проверить блокировку
                        }
                    }
                }
            }
        }
    }
}
