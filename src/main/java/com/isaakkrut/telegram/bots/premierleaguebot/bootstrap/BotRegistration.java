/*
package com.isaakkrut.telegram.bots.premierleaguebot.bootstrap;


import com.isaakkrut.telegram.bots.premierleaguebot.bot.PremierLeagueBot;
import com.isaakkrut.telegram.bots.premierleaguebot.services.DataLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

*/
/**
 * This class register our bot with Telegram Bots API
 *//*


@RequiredArgsConstructor
@Component
public class BotRegistration implements CommandLineRunner {

    private final PremierLeagueBot bot;


    @Override
    public void run(String... args) throws Exception {

        // Create the TelegramBotsApi object to register your bots
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            // Register bot
            botsApi.registerBot(bot);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
*/
