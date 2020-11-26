package com.isaakkrut.telegram.bots.premierleaguebot.bootstrap;


import com.isaakkrut.telegram.bots.premierleaguebot.bot.PremierLeagueBot;
import com.isaakkrut.telegram.bots.premierleaguebot.services.DataLoader;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

@RequiredArgsConstructor
@Component
public class BotRegistration implements CommandLineRunner {

    private final PremierLeagueBot bot;
    private final DataLoader dataLoader;


    @Override
    public void run(String... args) throws Exception {
        // Initializes dependencies necessary for the base bot
        ApiContextInitializer.init();

        // Create the TelegramBotsApi object to register your bots
        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            // Register your newly created AbilityBot
            botsApi.registerBot(bot);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
