package day03.ex01;

class PrintMessage implements Runnable {
    private String message;

    public PrintMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public void run() {
        System.out.println(message);
    }
}
