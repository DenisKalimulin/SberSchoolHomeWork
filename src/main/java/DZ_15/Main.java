package DZ_15;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        // Путь к текстовому файлу со ссылками
        String urlFilePath = "/Users/deniskalimulin/IdeaProjects/Test/SberSchoolHomeWork/src/main/java/DZ_15/urls.txt";  // Замените на свой путь
        String targetDirectory = "/Users/deniskalimulin/Downloads";  // Папка для сохранения файлов
        int downloadSpeedLimit = 500 * 1024;  // Ограничение 500 КБ/с в байтах

        // Чтение ссылок из файла
        LinkReader linkReader = new LinkReader();
        List<String> urls = linkReader.readLinksFromFile(urlFilePath);

        // Создание объекта для скачивания файлов
        FileDownloader fileDownloader = new FileDownloader(downloadSpeedLimit);

        // Создание директории для загрузки, если ее нет
        new File(targetDirectory).mkdirs();

        // Загрузка файлов
        fileDownloader.downloadFilesFromUrls(urls, targetDirectory);

        // Завершаем работу, дождавшись завершения всех потоков
        fileDownloader.shutdown();
    }
}
