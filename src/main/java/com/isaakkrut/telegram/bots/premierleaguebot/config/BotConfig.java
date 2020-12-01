package com.isaakkrut.telegram.bots.premierleaguebot.config;

import com.isaakkrut.telegram.bots.premierleaguebot.bot.PremierLeagueWebhookBot;
import com.isaakkrut.telegram.bots.premierleaguebot.services.responsehandlers.WebhookResponseHandler;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.ApiContext;
import org.telegram.telegrambots.bots.DefaultBotOptions;

@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "telegram")
@Configuration
public class BotConfig {

    private String botToken;
    private String botUsername;
    private int creatorId;
    private String botPath;

    @Autowired
    private WebhookResponseHandler responseHandler;


  /*  @Bean
    public PremierLeagueBot getBot(DataLoader dataLoader, TeamService teamService, ScorerService scorerService, AssistService assistService){
        return new PremierLeagueBot(botToken, botUsername, creatorId, dataLoader, teamService, scorerService, assistService);
    }*/

    @Bean
    public PremierLeagueWebhookBot getWebhookBot(){
        DefaultBotOptions options = ApiContext
                .getInstance(DefaultBotOptions.class);
        return new PremierLeagueWebhookBot(options, botUsername, botToken, creatorId, botPath, responseHandler);
    }

    //messages
    public static final String DB_FAVOURITE_TEAMS = "Favourite Teams";
    public static final String MAIN_MENU_MESSAGE = "What would you like to see today?";
    public static final String START_MESSAGE = "Welcome to the unofficial Premier League Bot";

    //menu items
    public static final String MENU_TOP_ASSISTS = "Top Assists";
    public static final String MENU_TOP_SCORERS = "Top Scorers";
    public static final String MENU_TABLE = "Table";
    public static final String MENU_SET_TEAM = "Set Favourite Team";
    public static final String MENU_TEAM = "Team";
    public static final String MENU_REMOVE_TEAM = "Remove Team";

    public static final String CREATOR_MENU_RELOAD_ALL = "Reload All";
    public static final String CREATOR_MENU_RELOAD_TABLE = "Reload Table";
    public static final String CREATOR_MENU_RELOAD_SCORERS = "Reload Scorers";
    public static final String CREATOR_MENU_RELOAD_ASSISTS = "Reload Assists";

    //command descriptions
    public static final String START_COMMAND_DESCRIPTION = "Menu Options";
    public static final String TABLE_COMMAND_DESCRIPTION = "Current table";
    public static final String TOP_SCORERS_COMMAND_DESCRIPTION = "Top Scorers";
    public static final String TOP_ASSISTS_COMMAND_DESCRIPTION = "Top Assists";
    //public static final String COMMANDS_COMMAND_DECRIPTION = "List of Available Commands";
    public static final String TEAM_COMMAND_DESCRIPTION = "See your favourite team";
    public static final String SET_TEAM_COMMAND_DESCRIPTION = "Set your favourite team";
    public static final String REMOVE_TEAM_COMMAND_DESCRIPTION = "Remove your favourite team";
    public static final String MENU_COMMAND_DESCRIPTION = "Open menu";
}
