package DZ_12.WithoutPatternPrototype;

import DZ_12.util.Sex;

public class Main {
    public static void main(String[] args) {
        User admin = new User("Denis Kalimulin", "Admin", Sex.MALE, 23);
        User user = new User("Victoria Surova", "User", Sex.FEMALE, 20);

        System.out.println(admin);
        System.out.println(user);
    }
}
/*
Без использования паттерна Prototype каждый пользователи создаются с нуля, Заново задаются свойства вручную.

 */