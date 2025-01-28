package DZ_15;

import java.io.*;
import java.net.*;
import java.util.List;
import java.util.concurrent.*;

public class FileDownloader {

    private final int maxDownloadSpeed;
    private final ExecutorService executorService;

    public FileDownloader(int maxDownloadSpeed) {
        this.maxDownloadSpeed = maxDownloadSpeed;
        this.executorService = Executors.newFixedThreadPool(3);
    }

    public void downloadFilesFromUrls(List<String> urls, String targetDirectory) {
        for (String url : urls) {
            executorService.submit(() -> downloadFile(url, targetDirectory));
        }
    }

    private void downloadFile(String urlStr, String targetDirectory) {
        try {
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            String fileName = url.getFile().substring(url.getFile().lastIndexOf("/") + 1);
            File targetFile = new File(targetDirectory, fileName);

            try (InputStream inputStream = connection.getInputStream();
                 FileOutputStream fileOutputStream = new FileOutputStream(targetFile)) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                long startTime = System.currentTimeMillis();
                long totalBytesDownloaded = 0;

                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    fileOutputStream.write(buffer, 0, bytesRead);
                    totalBytesDownloaded += bytesRead;

                    long elapsedTime = System.currentTimeMillis() - startTime;
                    if (elapsedTime > 0) {
                        long speed = totalBytesDownloaded / (elapsedTime / 1000);
                        if (speed > maxDownloadSpeed) {
                            long sleepTime = (totalBytesDownloaded / maxDownloadSpeed) - elapsedTime;
                            if (sleepTime > 0) {
                                Thread.sleep(sleepTime);
                            }
                        }
                    }
                }
                System.out.println("Скачался файл:  " + fileName);
            }
        } catch (IOException | InterruptedException e) {
            System.err.println("Ошибка скачивания файла с " + urlStr + ": " + e.getMessage());
        }
    }

    public void shutdown() {
        executorService.shutdown();
    }
}

