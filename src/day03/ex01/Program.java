package day03.ex01;

import java.util.Objects;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

public class Program {

    public static void main(String[] args) {
        if (args.length == 0 || !args[0].startsWith("--count=")) {
            System.err.println("Необходимо указать параметр --count=");
            System.exit(1);
        }

        try {
            Runnable task = getRunnable(args);

            Thread egg_thread = new Thread(task);
            Thread hen_thread = new Thread(task);
            egg_thread.start();
            hen_thread.start();

            egg_thread.join();
            hen_thread.join();
        } catch (NumberFormatException e) {
            System.err.println("Некорректное значение для параметра count");
            System.exit(1);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private static Runnable getRunnable(String[] args) {
        AtomicInteger counter = new AtomicInteger(Integer.parseInt(args[0].substring(8)));
        PrintMessage message = new PrintMessage("Egg");
        Semaphore semaphore = new Semaphore(1, true);

        return () -> {
            while (counter.getAndDecrement() > 0) {
                try {
                    semaphore.acquire();
                    System.out.println(Thread.currentThread());
                    message.run();
                    message.setMessage(Objects.equals(message.getMessage(), "Hen") ? "Egg" : "Hen");
                    semaphore.release();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        };
    }
}
