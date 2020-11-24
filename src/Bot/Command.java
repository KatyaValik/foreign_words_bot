package Bot;

public enum Command {
    WORD{
        public String getTextMessage(){
            return (new Reader()).readStringFromWords(false);
        }
    },
    HELP{
        public String getTextMessage(){
            return "/word - пришли мне эту команду, если хочешь, чтобы я скинул тебе какое-нибудь слово\n" +
                    "/help - команда, которая только что была тобой введена) здесь ты можешь узнать все команды, " +
                    "известные мне на данный момент";
        }
    },
    NOTHING{
        public String getTextMessage(){
            return "Прости, я не знаю такой команды :(\n" +
                    "Если хочешь узнать, какие команды мне уже известны, " +
                    "напиши мне /help";
        }
    };
    public abstract String getTextMessage();
}
