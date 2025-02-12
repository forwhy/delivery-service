package ru.hofftech.deliveryservice.telegramclient.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@Slf4j
public class TelegramService extends TelegramLongPollingBot {

    private final String username;
    private final String token;
    private final CommandProcessorService commandProcessorService;

    public TelegramService(String username, String token, CommandProcessorService commandProcessorService) {
        this.username = username;
        this.token = token;
        this.commandProcessorService = commandProcessorService;

        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
        } catch (Exception e){
            log.error("Ошибка при попытке установить соединение с телеграм-ботом.");
        }
    }

    @Override
    public String getBotUsername() {
        return username;
    }

    @Override
    public String getBotToken() {
        return token;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            log.info("Получено сообщение {}", messageText);
            var chatId = update.getMessage().getChatId();

            switch (messageText){
                case "/start":
                    sendStartMessage(update.getMessage().getChat().getFirstName(), chatId);
                    break;
                default:
                    String result = commandProcessorService.processCommandText(update.getMessage().getText());
                    sendMessage(result, chatId);
            }
        }
    }

    private void sendStartMessage(String name, long chatId) {
        sendMessage(
                String.format("Привет, %s. Для получения справки используйте команду /help.", name),
                chatId);
    }

    private void sendMessage(String textToSend, long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(textToSend);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            log.error("Ошибка при отправке сообщения в чат телеграм.");
        }
    }
}
