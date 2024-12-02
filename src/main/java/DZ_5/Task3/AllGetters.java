package DZ_5.Task3;

import DZ_5.Task2.Person;

import java.lang.reflect.Method;

public class AllGetters {
    public static void main(String[] args) {
        Class clazz = Person.class;
        Method[] methods = clazz.getDeclaredMethods();

        for (Method method : methods) {
            if(method.getName().startsWith("get")) {
                System.out.println(method.getName());
            }
        }
    }
}
