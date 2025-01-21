package DZ_14;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Calculator {
    private final Source source = new PostgreSQLSource(); // Источник кэша
    private final ObjectMapper objectMapper = new ObjectMapper(); // Для преобразования JSON

    @Cachable(PostgreSQLSource.class)
    public List<Integer> fibonachi(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("i должно быть больше 0");
        }

        try {
            String key = "fibonacci_" + i;

            String cachedResult = source.getResult(key);
            if (cachedResult != null) {
                // Возвращаем результат из кэша
                return objectMapper.readValue(cachedResult, List.class);
            }

            List<Integer> sequence = new ArrayList<>();
            int a = 0, b = 1;
            for (int j = 0; j < i; j++) {
                sequence.add(a);
                int sum = a + b;
                a = b;
                b = sum;
            }
            source.saveResult(key, objectMapper.writeValueAsString(sequence));
            return sequence;

        } catch (Exception e) {
            throw new RuntimeException("Ошибка при работе с кэшем: " + e.getMessage(), e);
        }
    }
}
