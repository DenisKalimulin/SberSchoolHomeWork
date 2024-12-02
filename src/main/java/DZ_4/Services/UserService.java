package DZ_4.Services;

import DZ_4.Models.User;

import java.math.BigDecimal;
import java.security.SecureRandom;

public class UserService {

    /**
     * Метод для создания аккаунта.
     * Генерирует номер счета с помощью приватного метода generateAccountNumber()
     * Хэширует пин-код с помощью приватного метода hashPin()
     * Выставляет баланс 0 при создании аккаунта
     * @param username Логин пользователя
     * @param pinCode пин-код
     * @return возвращает готового пользователя
     */
    public User createUser(String username, String pinCode){
        User user = new User();
        user.setUsername(username);
        user.setPinCode(pinCode);
        user.setBalance(BigDecimal.ZERO);
        user.setAccount(generateAccountNumber());
        return user;
    }

    /**
     * Генерирует случайный номер банковского счета, состоящий из 12 цифр.
     *
     * @return Сгенерированный номер счета в виде строки.
     */
    private String generateAccountNumber() {
        StringBuilder stringBuilder = new StringBuilder();
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 0; i < 12; i++) {
            stringBuilder.append(secureRandom.nextInt(10)); // Добавляем случайную цифру от 0 до 9
        }
        return stringBuilder.toString(); // Возвращаем сгенерированный номер счета
    }
}
