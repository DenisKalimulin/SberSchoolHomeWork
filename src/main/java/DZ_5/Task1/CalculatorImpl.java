package DZ_5.Task1;

public class CalculatorImpl implements Calculator {

    @Override
    public int calc(int number) {
        if (number <= 1) {
            return 1;
        }
        return number * calc(number - 1);
    }
}

