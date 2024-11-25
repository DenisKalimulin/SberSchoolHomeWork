package DZ_3;


import java.util.HashMap;
import java.util.Map;

public class CountMapImpl <T> implements CountMap<T> {

    private Map<T, Integer> map = new HashMap<>();

    @Override
    public void add(T t) {
        map.put(t, map.getOrDefault(t, 0) + 1);
    }

    @Override
    public int getCount(Object o) {
        return map.getOrDefault(o, 0) + 1;
    }

    @Override
    public int remove(Object o) {
        Integer count = map.remove(o);
        return (count == null) ? 0 : count;
    }

    @Override
    public int size() {
        return map.size();
    }

    @Override
    public void addAll(CountMap<T> source) {
        for (Map.Entry<T, Integer> entry : source.toMap().entrySet()) {
            this.add(entry.getKey());
        }
    }

    @Override
    public Map<T, Integer> toMap() {
        return new HashMap<>(map);
    }

    @Override
    public void toMap(Map<T, Integer> destination) {
        destination.putAll(map);
    }
}
