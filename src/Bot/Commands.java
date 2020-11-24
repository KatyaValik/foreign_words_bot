package Bot;

import java.util.HashMap;

public class Commands {
    private final HashMap<String, Command> commands;
    private Chats chats = new Chats();

    public Commands(){
        Command.setChats(chats);
        commands = new HashMap<String, Command>(){
            {
                put("/word", Command.WORD);
                put("/help", Command.HELP);
                put("/all", Command.ALL);
                put(".", Command.NOTHING);
            }
        };
    }

    public String getFromCommand(String string, Long chatId){
        if (commands.containsKey(string)){
            return commands.get(string).getTextMessage(chatId);
        }
        else return commands.get(".").getTextMessage(chatId);
    }
}