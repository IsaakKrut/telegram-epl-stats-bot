package com.isaakkrut.telegram.bots.premierleaguebot.services;

import com.isaakkrut.telegram.bots.premierleaguebot.config.BotConfig;
import com.isaakkrut.telegram.bots.premierleaguebot.config.KeyboardFactory;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;
import com.isaakkrut.telegram.bots.premierleaguebot.services.team.TeamService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Setter
public class ResponseHandler {
    private final MessageSender sender;
    private final Map<Long, String> favouriteTeams;

    private final TeamService teamService;

    private final DataLoader dataLoader;

    public ResponseHandler(MessageSender sender, DBContext db, TeamService teamService, DataLoader dataLoader) {
        this.sender = sender;
        favouriteTeams = db.getMap(BotConfig.DB_FAVOURITE_TEAMS);
        this.teamService = teamService;
        this.dataLoader = dataLoader;
    }

    public void replyToStart(Long chatId) {
        try {
            sender.execute(new SendMessage()
                    .setText(BotConfig.START_MESSAGE)
                    .setChatId(chatId));

           replyToMenu(chatId);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToButtons(Long chatId, String data) {
            switch (data) {
                case BotConfig.TABLE:
                    replyToTable(chatId);
                    break;
                case BotConfig.TOP_SCORERS:
                    replyToTopScorers(chatId);
                    break;
                case BotConfig.TOP_ASSISTS:
                    replyToTopAssists(chatId);
                    break;
                default:
                    setFavouriteTeam(chatId, data);
            }
    }

    public void replyToTopAssists(Long chatId)  {
        try {
            sender.execute(new SendMessage()
                    .setText("Top Assists: \n1.\n2.\n3.")
                    .setChatId(chatId));
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void replyToTopScorers(Long chatId) {
        try{
            sender.execute(new SendMessage()
                    .setText("Top Scorers: \n1.\n2.\n3.")
                    .setChatId(chatId));
        } catch (TelegramApiException e){
        e.printStackTrace();
        }
    }

    public void replyToTable(Long chatId) {
        StringBuilder message = new StringBuilder("Premier League table season 2020/2021\n\n");

        if (teamService == null){
            log.debug("Team service is null");
        }
        log.debug("Number of teams: " + teamService.getAllTeams().size());
        teamService.getAllTeams().stream()
                .sorted(Comparator.comparingInt(Team::getPlace))               //sort the table
                .forEach(team -> message.append(team.toString() + "\n"));

        message.append("P - points\nM - matches played\nW - wins\nD - draws\n" +
                "L - losses\nGS - goals scored\nGC - goals conceded");

        try{
            sender.execute(new SendMessage()
                    .setText(message.toString())
                    .setChatId(chatId));
        } catch (TelegramApiException e){
        e.printStackTrace();
        }
    }

    public void replyToCommands(Long chatId) {
        try{
            sender.execute(new SendMessage()
                    .setText("Here is the list of available commands:" +
                            "\n  /start - "+ BotConfig.START_COMMAND_DESCRIPTION +
                            "\n  /menu - "+ BotConfig.MENU_COMMAND_DESCRIPTION +
                            "\n  /table - " + BotConfig.TABLE_COMMAND_DESCRIPTION +
                            "\n  /topassists - " + BotConfig.TOP_ASSISTS_COMMAND_DESCRIPTION +
                            "\n  /topscorers - " + BotConfig.TOP_SCORERS_COMMAND_DESCRIPTION +
                            "\n  /team - " + BotConfig.TEAM_COMMAND_DESCRIPTION +
                            "\n  /setteam - " + BotConfig.SET_TEAM_COMMAND_DESCRIPTION +
                            "\n  /removeteam - " + BotConfig.REMOVE_TEAM_COMMAND_DESCRIPTION)

                    .setChatId(chatId));
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    public void setFavouriteTeam(Long chatId, String ... teamArgs){
        StringBuilder teamBuilder = new StringBuilder();
        for (String arg : teamArgs){
            teamBuilder.append(arg + " ");
        }
        String team = teamBuilder.toString().trim();
        if (teamService.getTeamByName(team)!= null){
            favouriteTeams.put(chatId, team);
            replyToTeam(chatId);
        }
        else {
            replyToSetTeam(chatId);
        }

    }

    public void removeFavouriteTeam(Long chatId){
        favouriteTeams.remove(chatId);
        try {
            sender.execute(new SendMessage()
                    .setText("Your favourite team has been successfully removed")
                    .setChatId(chatId));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToTeam(Long chatId) {
        if (favouriteTeams.containsKey(chatId)) {
            try {
                SendMessage message = new SendMessage().setChatId(chatId);
                String teamName = favouriteTeams.get(chatId);
                Team team = teamService.getTeamByName(teamName);

                if (team!= null){
                    message.setText(team.toString());
                } else{
                    message.setText("Team is not found."); // should not get here as we validate teams before setting them
                }
                sender.execute(message);

            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
        else{
            try {
                sender.execute(new SendMessage()
                        .setText("You haven't set your favourite team yet" )
                        .setChatId(chatId));
                replyToSetTeam(chatId);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public void replyToSetTeam(Long chatId) {
        try {
            sender.execute(new SendMessage()
                    .setText("Choose one of the following" )
                    .setChatId(chatId)
                    .setReplyMarkup(
                            KeyboardFactory.teamsList(teamService.getAllTeams()
                                                            .stream()
                                                            .map(Team::getTeam)//get team names to set up the buttons
                                                            .collect(Collectors.toList()))));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToMenu(Long chatId) {
        try {
            sender.execute(new SendMessage()
                    .setText(BotConfig.MAIN_MENU_MESSAGE)
                    .setChatId(chatId)
                    .setReplyMarkup(KeyboardFactory.mainMenu()));

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToLoadData(Long chatId) {
        int numOfUpdatesLeft = dataLoader.loadData(chatId);
        try {
            sender.execute(new SendMessage()
                    .setText("Data has been updated. You have " + numOfUpdatesLeft
                    + " updates left this month")
                    .setChatId(chatId));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }

    }
}
