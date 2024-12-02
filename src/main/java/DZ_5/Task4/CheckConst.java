package DZ_5.Task4;

import java.lang.reflect.Field;

public class CheckConst {
    public static void main(String[] args) {
        checkConstants(Days.class);
    }

    public static void checkConstants(Class<?> clazz) {
        Field[] fields = clazz.getDeclaredFields();

        for (Field field : fields) {
            if (field.getType() == String.class &&
                    java.lang.reflect.Modifier.isStatic(field.getModifiers()) &&
                    java.lang.reflect.Modifier.isFinal(field.getModifiers())) {
                try {
                    String value = (String) field.get(null);
                    if (!field.getName().equals(value)) {
                        System.out.println("Константа " + field.getName() + " имеет значение " + value + ", а не " + field.getName());
                    } else {
                        System.out.println("Константа " + field.getName() + " корректна.");
                    }
                } catch (IllegalAccessException e) {
                    System.err.println("Не удалось получить доступ к полю: " + field.getName());
                }
            }
        }
    }
}
