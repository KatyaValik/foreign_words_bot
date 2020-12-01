package Bot;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

public class Token {
    public static String get() {
        String toToken = ".\\src\\Bot\\token.txt";
        String token = "";
        try {
            token = new BufferedReader(new FileReader(toToken)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }
}