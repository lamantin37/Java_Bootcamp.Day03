package day03.ex00;


public class Program {

    public static void main(String[] args) {

        if (args[0].startsWith("--count=")) {
            try {
                int count = Integer.parseInt(args[0].substring(8));
                Thread egg_thread = new Thread(new PrintMessage("Egg", count));
                Thread hen_thread = new Thread(new PrintMessage("Hen", count));
                egg_thread.start();
                hen_thread.start();
            } catch (NumberFormatException e) {
                System.err.println("Некорректное значение для параметра count");
                System.exit(1);
            }
        }
    }
}
