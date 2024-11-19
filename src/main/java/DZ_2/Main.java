package DZ_2;

public class Main {
    public static void main(String[] args) {
        Task1.uniqueWords(Task1.words);
        System.out.println("----------------------------------");

        Task1.counterWords(Task1.words);
        System.out.println("-----------------------------------");

        ContactManager contactManager = new ContactManager();
        contactManager.add("Petrov", "123456789");
        contactManager.add("Fedorov", "929129324");
        contactManager.add("Ivanov", "0000000000");
        contactManager.add("Petrov", "9090909090");
        System.out.println(contactManager.get("Antonov"));
        System.out.println(contactManager.get("Petrov"));
        System.out.println(contactManager.get("Ivanov"));


    }
}
