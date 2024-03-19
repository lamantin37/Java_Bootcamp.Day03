package day03.ex02;
import java.util.Arrays;
import java.util.Random;


import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Program {

    static int numberOfThreads;
    static int arraySize;
    static final int defaultValue = 10;

    public static void main(String[] args) {
        parseArgs(args);

        try {
            int[] array = new int[arraySize];
            Random random = new Random();
            for (int i = 0; i < arraySize; i++) {
                array[i] = random.nextInt(2001) - 1000;
            }
            System.out.println("Sum: " + Arrays.stream(array).sum());

            AtomicInteger totalSum = new AtomicInteger(0);
            BlockingQueue<Runnable> work = Tasks.generateSumTasks(array, numberOfThreads, totalSum);
            ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(numberOfThreads);
            executor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

            for (Runnable task : work) {
                executor.execute(task);
            }

            executor.shutdown();
            System.out.println("Sum by threads: " + totalSum);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    private static void parseArgs(String[] args) {
        for (String arg : args) {
            if (arg.startsWith("--arraySize=")) {
                try {
                    arraySize = Integer.parseInt(arg.substring(12));
                } catch (NumberFormatException e) {
                    System.err.println("Неправильный формат аргумента для размера массива");
                    arraySize = defaultValue;
                }
            } else if (arg.startsWith("--threadsCount=")) {
                try {
                    numberOfThreads = Integer.parseInt(arg.substring(15));
                } catch (NumberFormatException e) {
                    System.err.println("Неправильный формат аргумента для количества потоков");
                    numberOfThreads = defaultValue;
                }
            }
        }

        if (arraySize == 0 || numberOfThreads == 0) {
            System.err.println("Оба аргумента (--arraySize и --threadsCount) должны быть указаны");
        }

        System.out.println("Размер массива: " + arraySize);
        System.out.println("Количество потоков: " + numberOfThreads);
    }
}

