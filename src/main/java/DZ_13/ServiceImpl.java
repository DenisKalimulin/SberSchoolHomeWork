package DZ_13;

import DZ_8.Service;

import java.util.ArrayList;
import java.util.List;

public class ServiceImpl implements Service {
    @Override
    public double doHardWork(String item, double value) {
        System.out.println("Calculating for " + item + ", " + value);
        return value * 42;
    }

    @Override
    public List<String> getData(String param) {
        List<String> result = new ArrayList<>();
        for (int i = 0; i < 1_000_000; i++) {
            result.add(param + i);
        }
        return result;
    }
}
