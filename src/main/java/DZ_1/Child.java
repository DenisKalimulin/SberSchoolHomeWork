package DZ_1;

public class Child extends Parent {
    static {
        System.out.println("Child:static 1");
    }

    {
        System.out.println("Child:instance 2");
    }

    static {
        System.out.println("Child:static 2");
    }

    public Child() {
        System.out.println("Child:constructor");
    }

    public Child(String name) {
        System.out.println("Child:name-constructor");
    }

    {
        System.out.println("Child:instance 2");
    }
}
