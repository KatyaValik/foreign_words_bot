package Bot;

import java.util.HashMap;

public class Commands {
    private final HashMap<String, Command> commands;

    public Commands(){
        commands = new HashMap<String, Command>(){
            {
                put("/word", Command.WORD);
                put("/help", Command.HELP);
                put(".", Command.NOTHING);
            }
        };
    }

    public String getFromCommand(String string){
        if (commands.containsKey(string)){
            return commands.get(string).getTextMessage();
        }
        else return commands.get(".").getTextMessage();
    }
}