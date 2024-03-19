package day03.ex00;

class PrintMessage implements Runnable {
    private final String message;
    private final int count;

    public PrintMessage(String message, int count) {
        this.message = message;
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 0; i < count; i++) {
            System.out.println(message);
        }
    }
}
