package Bot;

import java.util.HashMap;

public class Commands {
    private final HashMap<State, HashMap<String, Command>> commands;
    private Chats chats = new Chats();

    public Commands(){
        Command.setChats(chats);
        commands = new HashMap<State, HashMap<String, Command>>(){
            {
                put(State.FREE, new HashMap<String, Command>(){
                    {
                        put("/word", Command.WORD);
                        put("/help", Command.HELP);
                        put("/all", Command.ALL);
                        put(".", Command.NOTHING);
                    }
                });
                put(State.WAITING_FOR_ANSWER, new HashMap<String, Command>(){
                    {
                        put("/yes", Command.LEARNED);
                        put("/help", Command.HELP);
                        put(".", Command.SHOWN);
                    }
                });
            }
        };
    }

    public String getFromCommand(String string, Long chatId){
        State state = chats.getCurrentState(chatId);
        if (commands.get(state).containsKey(string)){
            return commands.get(state).get(string).getTextMessage(chatId);
        }
        else return commands.get(state).get(".").getTextMessage(chatId);
    }
}