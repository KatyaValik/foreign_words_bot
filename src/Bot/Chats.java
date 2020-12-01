package Bot;

import java.sql.SQLException;
import java.util.*;

public class Chats {
    private DbHandler db;
    private final int max;

    public Chats() {
        try{
            db = DbHandler.getInstance();
        } catch (SQLException e){
            e.printStackTrace();
        }
        max = db.getAllWords().size();
    }

    public boolean appendWordToChat(Long chatId, String word){
        HashMap<String, String> words = db.getWordsFromUser(chatId);
        String foreignWord = word.split(" - ")[0];
        if (!words.containsKey(foreignWord)){
            db.addWordToUser(chatId, foreignWord);
        }
        return (max == words.size());
    }

    public ArrayList<String> getAllWords(Long chatId){
        ArrayList<String> result = new ArrayList<String>();
        for (Map.Entry<String, String> entry: db.getWordsFromUser(chatId).entrySet()){
            result.add(entry.getKey() + " - " + entry.getValue());
        }
        return result;
    }

    public String getRandomNewWord(Long chatId){
        HashMap<String, String> dictionary = db.getAllWords();
        for (String key: db.getWordsFromUser(chatId).keySet()){
            dictionary.remove(key);
        }
        Set<String> keys = dictionary.keySet();

        Random rand = new Random();
        String key = (String) keys.toArray()[rand.nextInt(keys.size())];
        return key + " - " + dictionary.get(key);
    }
}