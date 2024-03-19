package day03.ex03;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class UrlReader {
    public static String[] reader() {
        ArrayList<String> urls = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(
                new FileReader("src/day03/ex03/urls.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                urls.add(line.trim());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return urls.toArray(new String[0]);
    }
}
