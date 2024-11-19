package DZ_2;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class SpeedTestArrayListVSLinkedList {
    private static final int OPERATIONS = 1000000;
    private static long startTime;
    private static long endTime;

    public static void main(String[] args) {
        arrayListTest();
        System.out.println("--------");
        linkedListTest();
    }

    private static void arrayListTest() {
        List<Integer> arrayList = new ArrayList<>();
        //Вставка элементов
        startTime = System.currentTimeMillis();
        for (int i = 0; i < OPERATIONS; i++) {
            arrayList.add(i);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Вставка элементов в ArrayList: " + (endTime - startTime) + "mc");

        //Получение элемента по индексу, начиная с 0
        startTime = System.currentTimeMillis();
        for (int i = 0; i < OPERATIONS; i++) {
            arrayList.get(i);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Получение элемента по индексу в ArrayList: " + (endTime - startTime) + "mc");

        //Время удаления первого элемента
        for (int i = 0; i < OPERATIONS; i++) {
            arrayList.add(i);
        }
        startTime = System.currentTimeMillis();
        arrayList.remove(0);
        endTime = System.currentTimeMillis();
        System.out.println("Время удаления первого элемента в ArrayList: " + (endTime - startTime) + "mc");
    }

    private static void linkedListTest() {
        List<Integer> linkedList = new LinkedList<>();
        //Вставка элементов
        startTime = System.currentTimeMillis();
        for (int i = 0; i < OPERATIONS; i++) {
            linkedList.add(i);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Вставка элементов в LinkedList: " + (endTime - startTime) + "mc");

        //Получение элемента по индексу, начиная с 0
        startTime = System.currentTimeMillis();
        for (int i = 0; i < OPERATIONS; i++) {
            linkedList.get(i);
        }
        endTime = System.currentTimeMillis();
        System.out.println("Получение элемента по индексу в LinkedList: " + (endTime - startTime) + "mc");

        //Время удаления первого элемента
        for (int i = 0; i < OPERATIONS; i++) {
            linkedList.add(i);
        }
        startTime = System.currentTimeMillis();
        linkedList.remove(0);
        endTime = System.currentTimeMillis();
        System.out.println("Время удаления первого элемента в LinkedList: " + (endTime - startTime) + "mc");
    }
}
