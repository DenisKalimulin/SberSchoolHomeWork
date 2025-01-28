package DZ_15;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) {

        String urlFilePath = "/Users/deniskalimulin/IdeaProjects/Test/SberSchoolHomeWork/src/main/java/DZ_15/urls.txt";
        String targetDirectory = "/Users/deniskalimulin/Downloads";
        int downloadSpeedLimit = 500 * 1024;

        LinkReader linkReader = new LinkReader();
        List<String> urls = linkReader.readLinksFromFile(urlFilePath);

        FileDownloader fileDownloader = new FileDownloader(downloadSpeedLimit);

        new File(targetDirectory).mkdirs();

        fileDownloader.downloadFilesFromUrls(urls, targetDirectory);

        fileDownloader.shutdown();
    }
}
