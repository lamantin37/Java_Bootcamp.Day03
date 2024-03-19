package day03.ex03;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public class Program {
    public static void main(String[] args) {

        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(4);
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        BlockingQueue<Runnable> work = Downloader.downloadTasks();

        for (Runnable task: work) {
            executor.execute(task);
        }

        executor.shutdown();
    }
}
