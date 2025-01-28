package DZ_15;

import java.io.*;
import java.util.*;

public class LinkReader {

    public List<String> readLinksFromFile(String filePath) {
        List<String> urls = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                urls.add(line.trim());
            }
        } catch (IOException e) {
            System.err.println("Ошибка чтения файла: " + e.getMessage());
        }
        return urls;

    }
}