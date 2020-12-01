package com.isaakkrut.telegram.bots.premierleaguebot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.telegram.telegrambots.ApiContextInitializer;

@SpringBootApplication
public class PremierleaguebotApplication {

    public static void main(String[] args) {
        // Initializes dependencies necessary for the bot
        ApiContextInitializer.init();

        SpringApplication.run(PremierleaguebotApplication.class, args);
    }

}
