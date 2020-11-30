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
    private static final int max = Integer.parseInt(getStringFromWords(true));
    private static List<String> words = readAllWords();
    public static final String path_to_words = "out/production/foreign_words_bot/Bot/words.txt";

    public static List<String> readAllWords(){
        List<String> lines = new ArrayList<>();
        Path toWords = Paths.get(path_to_words);
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
        String token = "1411724979:AAHJ_hnVZ00woDAdnzVnAiuDC6rIEmQx6xs";
        return token;
    }

    public static String getStringFromWords(boolean first)
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

    public static String readToLine(int lineNumber){
        String line = "";
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(path_to_words));
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

    public int getMax(){
        return max;
    }
}