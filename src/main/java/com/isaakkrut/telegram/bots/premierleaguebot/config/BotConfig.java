package com.isaakkrut.telegram.bots.premierleaguebot.config;

import com.isaakkrut.telegram.bots.premierleaguebot.PremierLeagueBot;
import com.isaakkrut.telegram.bots.premierleaguebot.services.DataLoader;
import com.isaakkrut.telegram.bots.premierleaguebot.services.team.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Setter
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "telegram")
@Configuration
public class BotConfig {

    private String botToken;
    private String botUsername;
    private int creatorId;

    @Bean
    public PremierLeagueBot getBot(TeamService teamService, DataLoader dataLoader){
        return new PremierLeagueBot(botToken, botUsername, creatorId, teamService, dataLoader);
    }

    //messages
    public static final String DB_FAVOURITE_TEAMS = "Favourite Teams";
    public static final String MAIN_MENU_MESSAGE = "What would you like to see today?";
    public static final String START_MESSAGE = "Welcome to the unofficial Premier League Bot";
    public static final String TOP_ASSISTS = "Top Assists";
    public static final String TOP_SCORERS = "Top Scorers";
    public static final String TABLE = "Table";

    //command descriptions
    public static final String START_COMMAND_DESCRIPTION = "Menu Options";
    public static final String TABLE_COMMAND_DESCRIPTION = "Current table";
    public static final String TOP_SCORERS_COMMAND_DESCRIPTION = "Top Scorers";
    public static final String TOP_ASSISTS_COMMAND_DESCRIPTION = "Top Assists";
    public static final String COMMANDS_COMMAND_DECRIPTION = "List of Available Commands";
    public static final String TEAM_COMMAND_DESCRIPTION = "See your favourite team";
    public static final String SET_TEAM_COMMAND_DESCRIPTION = "Set your favourite team";
    public static final String REMOVE_TEAM_COMMAND_DESCRIPTION = "Remove your favourite team";
    public static final String MENU_COMMAND_DESCRIPTION = "Open menu";
}
