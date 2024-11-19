package DZ_2;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ContactManager {
    private final HashMap<String, List<String>> contacts;

    public ContactManager() {
        contacts = new HashMap<>();
    }

    /**
     * Метод добавляет фамилию человека, и его номер телефона
     * <p/> Если фамилия уже есть в списке контактов,
     * номер добавляется к существующему списку номеров с этой фамилией.
     * Если фамилии нет в списке, создается новый список для хранения номеров.
     *
     * @param surname фамилия, к которой добавляется номер телефона.
     * @param number номер телефона, который необходимо добавить.
     */
    public void add(String surname, String number){
        contacts.computeIfAbsent(surname, k -> new ArrayList<>()).add(number);
    }

    /**
     * Метод получает список номеров по указанной фамилии.
     * Если фамилия существует в списке контактов, возвращается список номеров телефонов.
     * Если фамилия не найдена, вернется пустой список.
     * @param surname фамилия, для которой необходимо получить список номеров
     * @return список номеров, связанных с указанной фамилией
     */
    public List<String> get(String surname){
        return contacts.getOrDefault(surname, new ArrayList<>());
    }
}

