package DZ_25.GC;

import java.util.ArrayList;
import java.util.List;

public class GCAnalysis {
    public static void main(String[] args) {
        List<byte[]> objects = new ArrayList<>();

        for (int i = 0; i < 100000; i++) {
            objects.add(new byte[1024]);
            if (i % 1000 == 0) {
                System.out.println("Создано объектов: " + i);
            }
        }

        System.out.println("Объекты созданы, следим за GC...");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}