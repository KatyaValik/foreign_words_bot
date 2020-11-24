package Bot;

public enum Command {
    WORD{
        public String getTextMessage(Long chatId){
            String message = Reader.getStringFromWords(false);
            boolean thisWord = getChats().appendWordToChat(chatId, message);
            while(!thisWord) {
                message = Reader.getStringFromWords(false);
                thisWord = getChats().appendWordToChat(chatId, message);
            }
            return message;
        }
    },
    HELP{
        public String getTextMessage(Long chatId){
            return "/word - пришли мне эту команду, если хочешь, чтобы я скинул тебе какое-нибудь слово\n" +
                    "/all - все уже присланные мною тебе слова\n" +
                    "/help - команда, которая только что была тобой введена) здесь ты можешь узнать все команды, " +
                    "известные мне на данный момент";
        }
    },
    ALL{
        public String getTextMessage(Long chatId){
            return getChats().getAllWords(chatId).toString();
        }
    },
    NOTHING{
        public String getTextMessage(Long chatId){
            return "Прости, я не знаю такой команды :(\n" +
                    "Если хочешь узнать, какие команды мне уже известны, " +
                    "напиши мне /help";
        }
    };
    public abstract String getTextMessage(Long chatId);
    private static Chats chats;

    public static void setChats(Chats chat) {
        chats = chat;
    }

    public Chats getChats(){
        return chats;
    }
}