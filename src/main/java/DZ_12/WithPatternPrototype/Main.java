package DZ_12.WithPatternPrototype;

import DZ_12.util.Sex;

public class Main {
    public static void main(String[] args) throws CloneNotSupportedException {
        User templateUser = new User("Default User", "User", Sex.MALE, 18);

        User admin = templateUser.clone();
        admin.setFullName("Denis Kalimulin");
        admin.setAge(23);
        admin.setRole("Admin");

        User user = templateUser.clone();
        user.setFullName("Victoria Surova");
        user.setAge(20);
        user.setRole("User");
        user.setGender(Sex.FEMALE);

        System.out.println(templateUser);
        System.out.println(admin);
        System.out.println(user);
    }
}
/*
С паттерном Prototype каждый пользователь создается путем клонирования шаблона и меняются только свойства, которые могут отличаться.
Все объекты будут и отталкиваться от шаблона
 */
