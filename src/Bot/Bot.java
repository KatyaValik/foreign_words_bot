package Bot;

import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.HashMap;

public class Bot extends TelegramLongPollingBot {
    Commands commands = new Commands();
    ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup();

    public static void main(String[] args) {
        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new Bot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getBotUsername() {
        return "foreign_words_bot";
    }

    @Override
    public String getBotToken() {
        return Token.get();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userMessage = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String messageText = commands.getFromCommand(userMessage, chatId);

            SendMessage message = new SendMessage()
                    .setChatId(chatId)
                    .setText(messageText);
            message.setReplyMarkup(replyKeyboardMarkup);
            try {
                message.setText(getMessage(userMessage, messageText, chatId));
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public String getMessage(String msg, String msgText, long chatId){
        ArrayList<KeyboardRow> keyboard = new ArrayList<KeyboardRow>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(true);

        State state = commands.getState(chatId);

        if(state.equals(State.FREE)){
            keyboard.clear();
            keyboardFirstRow.add("word");
            keyboardFirstRow.add("all");
            keyboardSecondRow.add("help");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return msgText;
        }

        if(state.equals(State.WAITING_FOR_ANSWER)) {
            keyboard.clear();
            keyboardFirstRow.add("yes");
            keyboardFirstRow.add("no");
            keyboardSecondRow.add("help");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return msgText;
        }
        return "Если возникли проблемы, воспользуйтесь /start";
    }
}
