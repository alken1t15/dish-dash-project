package com.example.dishdash.bot.config;


import com.example.dishdash.entity.OrderUser;
import com.example.dishdash.entity.UsersTelegram;
import com.example.dishdash.service.ServiceOrderUser;
import com.example.dishdash.service.ServiceUsersTelegram;
import com.vdurmont.emoji.EmojiParser;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.commands.SetMyCommands;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.commands.BotCommand;
import org.telegram.telegrambots.meta.api.objects.commands.scope.BotCommandScopeDefault;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
public class TelegramBot extends TelegramLongPollingBot {

    @Autowired
    private ServiceUsersTelegram serviceUsersTelegram;
    @Autowired
    private ServiceOrderUser serviceOrderUser;
    private final BotConfig config;

    static final String HELP_TEXT = """
            Добро пожаловать этот бот предназначен для просмотра заказа

            Вот список команд которые вы можете использовать:

            Введите /start, чтобы увидеть приветственное сообщение

            Введите /status, чтобы увидеть статус заказа

            Введите /help чтобы увидеть это сообщение снова""";

    public TelegramBot(BotConfig config) {
        this.config = config;
        List<BotCommand> listofCommands = new ArrayList();
        listofCommands.add(new BotCommand("/start", "введите, чтобы увидеть приветственное сообщение"));
        listofCommands.add(new BotCommand("/status", "введите чтобы увидеть статус заказа"));
        listofCommands.add(new BotCommand("/help", "чтобы увидеть информацию о помощи"));
     //   listofCommands.add(new BotCommand("/register", "delete my data"));
        try {
            this.execute(new SetMyCommands(listofCommands, new BotCommandScopeDefault(), null));
        } catch (TelegramApiException e) {
            log.error("Error setting bot's command list: " + e.getMessage());
        }
    }

    @Override
    public String getBotUsername() {
        return config.getBotName();
    }

    @Override
    public String getBotToken() {
        return config.getToken();
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            long chatId = update.getMessage().getChatId();
            UsersTelegram user = serviceUsersTelegram.findByIdUser(update.getMessage().getChat().getId());
            if (user!= null){
                if (user.getStatus()){
                    status(chatId,user,update.getMessage().getText());
                    return;
                }
            }
            switch (messageText) {
                case "/start" -> {
                    registerUser(update.getMessage());
                    startCommandReceived(chatId, update.getMessage().getChat().getFirstName());
                }
                case "/help" -> sendMessage(chatId, HELP_TEXT);
//                case "/register":
//                    register(chatId);
//                    break;
                case "/status" -> {
                    registerUser(update.getMessage());
                    sendMessage(chatId, "Введите номер телефона ");
                    user.setStatus(true);
                    serviceUsersTelegram.save(user);
                }
                default -> sendMessage(chatId, "Извините, такой команды нету");
            }
        }
    }

    private void status(long chatId, UsersTelegram userTelegram, String text) {
        List<OrderUser> orderUsers = serviceOrderUser.findAllByPhone(Long.valueOf(text));
        if (orderUsers == null){
            sendMessage(chatId, "Заказа на такой номер телефона нету\n" +
                    "Попробуйте заново ввести номер телефона");
        }
        else {
            if (orderUsers.isEmpty()){
                sendMessage(chatId, "На данный момент нету заказов");
                userTelegram.setStatus(false);
                serviceUsersTelegram.save(userTelegram);
            }
            else {
                StringBuilder stringBuilder = new StringBuilder();
                for (OrderUser orderUser : orderUsers){
                    stringBuilder.append("Статус заказа: " + orderUser.getStatus() + " заказан на адрес: " + orderUser.getAddress() +"\n");
                }
                sendMessage(chatId, String.valueOf(stringBuilder));
                userTelegram.setStatus(false);
                serviceUsersTelegram.save(userTelegram);
            }
        }
    }

    private void registerUser(Message msg) {
        if (serviceUsersTelegram.findByIdUser(msg.getChatId()) == null) {
            var idUser = msg.getChat().getId();
            UsersTelegram user = new UsersTelegram(idUser, false);
            serviceUsersTelegram.save(user);
        }
    }

    private void startCommandReceived(long chatId, String name) {
        String answer = EmojiParser.parseToUnicode("Привет, " + name + ", приятно с тобой познакомиться!" + " :blush:\n" +
                "Чтобы посмотреть статус заказа введите команду: /status");
        sendMessage(chatId, answer);
    }

    private void sendMessage(long chatId, String textToSent) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSent);

        ReplyKeyboardMarkup keyboardMarkup = new ReplyKeyboardMarkup();

        List<KeyboardRow> keyboardRows = new ArrayList<>();

        KeyboardRow row = new KeyboardRow();

        row.add("/start");
        row.add("/status");

        keyboardRows.add(row);

        row = new KeyboardRow();

     //   row.add("register");

        keyboardRows.add(row);

        keyboardMarkup.setKeyboard(keyboardRows);

        message.setReplyMarkup(keyboardMarkup);

        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Error occurred: " + e.getMessage());
        }
    }
}