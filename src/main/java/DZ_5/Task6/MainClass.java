package DZ_5.Task6;

import DZ_5.Task1.Calculator;
import DZ_5.Task1.CalculatorImpl;

public class MainClass {
    public static void main(String[] args) {
        Calculator calculator = PerfomanceProxy.createProxy(new CalculatorImpl());
        System.out.println(calculator.calc(3));
    }
}
