package DZ_5.Task1;

import DZ_5.Task6.Metric;

public interface Calculator {
    /**
     * Расчет факториала числа.
     * @param number число для которого рассчитывается факториал
     */
    @Metric
    int calc (int number);
}
