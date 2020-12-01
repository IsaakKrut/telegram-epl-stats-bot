package com.isaakkrut.telegram.bots.premierleaguebot.bot;

import com.isaakkrut.telegram.bots.premierleaguebot.services.responsehandlers.WebhookResponseHandler;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramWebhookBot;

public class PremierLeagueWebhookBot extends TelegramWebhookBot {

    private final String botUsername;
    private final String botToken;
    private final int creatorId;
    private final String botPath;
    private final WebhookResponseHandler responseHandler;

    public PremierLeagueWebhookBot(DefaultBotOptions options, String botUsername, String botToken, int creatorId, String botPath, WebhookResponseHandler responseHandler) {
        super(options);
        this.botUsername = botUsername;
        this.botToken = botToken;
        this.creatorId = creatorId;
        this.botPath = botPath;
        this.responseHandler = responseHandler;
    }

    @Override
    public BotApiMethod onWebhookUpdateReceived(Update update) {

        return responseHandler.handleUpdate(update);
    }

    @Override
    public String getBotUsername() {
        return this.botUsername;
    }

    @Override
    public String getBotToken() {
        return this.botToken;
    }

    @Override
    public String getBotPath() {
        return this.botPath;
    }
}
