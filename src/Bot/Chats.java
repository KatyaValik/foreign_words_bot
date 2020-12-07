package Bot;

import java.sql.SQLException;
import java.util.*;

public class Chats {
    private DbHandler db;
    private final int max;
    private HashMap<Long, State> states = new HashMap<Long, State>();
    private HashMap<Long, String> lastShown = new HashMap<Long, String>();

    public Chats() {
        try{
            db = DbHandler.getInstance();
        } catch (SQLException e){
            e.printStackTrace();
        }
        max = db.getAllWords().size();
    }

    public void appendWordToChat(Long chatId, String word){
        changeState(chatId, State.WAITING_FOR_ANSWER);
        changeLastLearned(chatId, word);
    }

    public boolean appendLearnedWord(Long chatId, boolean isLearned){
        String word = lastShown.get(chatId);
        HashMap<String, String> words = db.getWordsFromUser(chatId);
        String foreignWord = word.split(" - ")[0];
        if (!words.containsKey(foreignWord)){
            db.addWordToUser(chatId, foreignWord, isLearned);
        }
        states.put(chatId, State.FREE);
        return max == words.size();
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
        if (keys.isEmpty())
            return "";

        Random rand = new Random();
        String key = (String) keys.toArray()[rand.nextInt(keys.size())];
        return key + " - " + dictionary.get(key);
    }

    public String getRandomLearnedWord(Long chatId){
        HashMap<String, String> dictionary = db.getWordsFromUser(chatId);
        Random rand = new Random();
        Set<String>keys = dictionary.keySet();
        String key = (String) keys.toArray()[rand.nextInt(keys.size())];
        return key + " - " + dictionary.get(key);
    }

    public State getCurrentState(Long chatId){
        if (states.containsKey(chatId)){
            return states.get(chatId);
        }
        states.put(chatId, State.FREE);
        return State.FREE;
    }

    public void changeState(Long chatId, State newState){
        states.put(chatId, newState);
    }

    public void changeLastLearned(Long chatId, String newLearned){
        lastShown.put(chatId, newLearned);
    }
}