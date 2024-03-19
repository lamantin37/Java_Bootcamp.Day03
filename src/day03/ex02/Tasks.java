package day03.ex02;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;

public class Tasks {
    public static BlockingQueue<Runnable> generateSumTasks(int[] array, int numberOfThreads, AtomicInteger totalSum) {
        BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(numberOfThreads);

        int arraySize = array.length;
        int chunkSize = arraySize / numberOfThreads;
        int startIndex = 0;

        for (int i = 0; i < numberOfThreads; i++) {
            int endIndex = (i == numberOfThreads - 1) ? arraySize : startIndex + chunkSize;
            final int start = startIndex;
            final int end = endIndex;

            queue.offer(() -> {
                int sum = 0;
                for (int j = start; j < end; j++) {
                    sum += array[j];
                }
                System.out.println(Thread.currentThread() +
                        ": from " + start + " to " + (end - 1) + " sum is " + sum);
                totalSum.addAndGet(sum);
            });

            startIndex = endIndex;
        }

        return queue;
    }
}
