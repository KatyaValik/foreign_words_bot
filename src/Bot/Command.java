package Bot;


public enum Command {
    WORD{
        public String getTextMessage(Long chatId){
            String message = getChats().getRandomNewWord(chatId);
            if (message.isEmpty())
                message = getChats().getRandomLearnedWord(chatId);
            getChats().appendWordToChat(chatId, message.split(" - ")[0]);
            return message;
        }
    },
    HELP{
        public String getTextMessage(Long chatId){
            State state = getChats().getCurrentState(chatId);
            return switch (state) {
                case FREE -> "/word - пришли мне эту команду, если хочешь, чтобы я скинул тебе какое-нибудь слово\n" +
                        "/all - все уже присланные мною тебе слова\n" +
                        "/help - команда, которая только что была тобой введена) здесь ты можешь узнать " +
                        "все команды, известные мне на данный момент";
                case WAITING_FOR_ANSWER -> "/yes - так я пойму, что ты осознал и принял данное слово и больше не " +
                        "буду его показывать\n" +
                        "/help - как всегда, эта команда приведет нас к тому, что я подскажу, что я умею делать " +
                        "в данном состоянии\n" +
                        "любая абракадабра - я пойму, что последнее слово, которое я скинул, тебе " +
                        "ещё не известно. Это не повод отчаиваться)";
            };

        }
    },
    ALL{
        public String getTextMessage(Long chatId){
            return getChats().getAllWords(chatId).toString();
        }
    },
    ALL_LEARNED{
        public String getTextMessage(Long chatId){
            return "Прости, но я ещё не научился обрабатывать данный запрос(";
        }
    },
    NOTHING{
        public String getTextMessage(Long chatId){
            return "Прости, я не знаю такой команды :(\n" +
                    "Если хочешь узнать, какие команды мне уже известны, " +
                    "напиши мне /help";
        }
    },
    LEARNED{
        public String getTextMessage(Long chatId){
            getChats().appendLearnedWord(chatId, true);
            return "Прекрасно, теперь ты знаешь ещё одно слово)";
        }
    },
    SHOWN{
        public String getTextMessage(Long chatId){
            getChats().appendLearnedWord(chatId, false);
            return "Продолжай в том же духе и все получится)";
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