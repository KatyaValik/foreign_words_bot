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

public class Bot extends TelegramLongPollingBot {
    Commands commands = new Commands();
    Chats chats = new Chats();
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
        return "@foreign_words_bot";
    }

    @Override
    public String getBotToken() {
        return Reader.getBotToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String userMessage = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            String messageText = commands.getFromCommand(userMessage, chatId);

            SendMessage message = new SendMessage()
                    .setChatId(chatId)
                    .setText(getMessage(update.getMessage().getText()));
            message.setReplyMarkup(replyKeyboardMarkup);
            try {
                message.setText(getMessage(messageText));
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }
    public String getMessage(String msg) {
        ArrayList<KeyboardRow> keyboard = new ArrayList<>();
        KeyboardRow keyboardFirstRow = new KeyboardRow();
        KeyboardRow keyboardSecondRow = new KeyboardRow();

        replyKeyboardMarkup.setSelective(true);
        replyKeyboardMarkup.setResizeKeyboard(true);
        replyKeyboardMarkup.setOneTimeKeyboard(false);

        if (msg.equals("help") || msg.equals("all") || (msg.equals("/start"))) {
            keyboard.clear();
            keyboardFirstRow.add("word");
            keyboardFirstRow.add("all");
            keyboardSecondRow.add("help");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Choose...";
        }
        if (msg.equals("word")) {
            keyboard.clear();
            keyboardFirstRow.add("I know this word");
            keyboardFirstRow.add("I don't know this word");
            keyboardSecondRow.add("help");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Choose ...";
        }
        if (msg.equals("I know this word") || (msg.equals("I don't know this word"))
        ) {
            keyboard.clear();
            keyboardFirstRow.add("word");
            keyboardFirstRow.add("all");
            keyboardSecondRow.add("help");
            keyboard.add(keyboardFirstRow);
            keyboard.add(keyboardSecondRow);
            replyKeyboardMarkup.setKeyboard(keyboard);
            return "Choose...";
        }
    return "if problems...";
    }
}