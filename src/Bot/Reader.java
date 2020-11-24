package Bot;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Reader {
    private final int max = Integer.parseInt(this.readStringFromWords(true));
    private static List<String> words = readAllWords();

    public static List<String> readAllWords(){
        List<String> lines = new ArrayList<>();
        Path toWords = Paths.get(".\\src\\Bot\\words.txt");
        try {
            lines = Files.readAllLines(toWords, Charset.defaultCharset());
            lines.remove(0);
            lines.remove(0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public static String getBotToken() {
        String toToken = ".\\src\\Bot\\token.txt";
        String token = "";
        try {
            token = new BufferedReader(new FileReader(toToken)).readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return token;
    }

    public String readStringFromWords(boolean first)
    {
        String line = "";
        if (first){
            line = readToLine(1);
        }
        else{
            Random random = new Random();
            int next_word = random.nextInt(max);
            line = words.get(next_word);
        }
        return line;
    }

    public String readToLine(int lineNumber){
        String toWords = ".\\src\\Bot\\words.txt";
        String line = "";
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(toWords));
            line = reader.readLine();
            while (lineNumber > 0) {
                lineNumber--;
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }
}
