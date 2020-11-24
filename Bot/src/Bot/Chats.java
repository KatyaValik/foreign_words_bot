package Bot;

import java.util.ArrayList;
import java.util.HashMap;

public class Chats {
    private HashMap<Long, ArrayList> chats;
    private int max = (new Reader()).getMax();

    public Chats() {
        chats = new HashMap<Long, ArrayList>();
    }

    public boolean appendWordToChat(Long chatId, String word){
        if (chats.containsKey(chatId) && !chats.get(chatId).contains(word)){
            chats.get(chatId).add(word);
            return true;
        }
        else if (!chats.containsKey(chatId)){
            ArrayList<String> list = new ArrayList<String>(){
                {
                    add(word);
                }
            };
            chats.put(chatId, list);
            return true;
        }
        else if (max == chats.get(chatId).size())
            return true;
        return false;
    }

    public ArrayList getAllWords(Long chatId){
        return chats.get(chatId);
    }
}
