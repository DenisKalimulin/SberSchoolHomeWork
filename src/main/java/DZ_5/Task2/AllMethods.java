package DZ_5.Task2;

import java.lang.reflect.Method;

public class AllMethods {
    public static void main(String[] args) {
        Class clazz = Person.class;
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }
    }
}
