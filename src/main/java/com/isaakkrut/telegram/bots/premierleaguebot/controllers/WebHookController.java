package com.isaakkrut.telegram.bots.premierleaguebot.controllers;


import com.isaakkrut.telegram.bots.premierleaguebot.bot.PremierLeagueWebhookBot;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.objects.Update;


@RequiredArgsConstructor
@RestController
public class WebHookController {

    private final PremierLeagueWebhookBot bot;

    /**
     * This method receives WebHooks from Telegram API whenever a user sends a request.
     * @param update
     * @return
     */

    @PostMapping("/")
    BotApiMethod onWebhookReceiver(@RequestBody Update update){
        return bot.onWebhookUpdateReceived(update);
    }
}
