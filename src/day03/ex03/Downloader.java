package day03.ex03;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Downloader {
    static final String saveDir = "src/day03/ex03/pic/";

    static BlockingQueue<Runnable> downloadTasks() {
        String[] urls = UrlReader.reader();
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(urls.length);

        for (String link : urls) {
            queue.offer(() -> {
                try {
                    URL url = new URL(link);
                    URLConnection conn = url.openConnection();
                    InputStream inputStream = conn.getInputStream();

                    String fileName = url.getFile();
                    fileName = fileName.substring(fileName.lastIndexOf("/") + 1);

                    FileOutputStream outputStream = new FileOutputStream(saveDir + fileName);

                    System.out.println(Thread.currentThread() + " start download file " + fileName);
                    byte[] buffer = new byte[4096];
                    int bytesRead;
                    while ((bytesRead = inputStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, bytesRead);
                    }

                    outputStream.close();
                    inputStream.close();

                    System.out.println(Thread.currentThread() + " finish download file " + fileName);
                } catch (IOException e) {
                    System.out.println(Thread.currentThread() + " interrupted downloading");
                    e.printStackTrace();
                }
            });
        }
        return queue;
    }
}
