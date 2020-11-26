package com.isaakkrut.telegram.bots.premierleaguebot.bot;

import com.isaakkrut.telegram.bots.premierleaguebot.config.BotConfig;
import com.isaakkrut.telegram.bots.premierleaguebot.config.KeyboardFactory;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Assist;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Scorer;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;
import com.isaakkrut.telegram.bots.premierleaguebot.services.DataLoader;
import com.isaakkrut.telegram.bots.premierleaguebot.services.assist.AssistService;
import com.isaakkrut.telegram.bots.premierleaguebot.services.scorer.ScorerService;
import com.isaakkrut.telegram.bots.premierleaguebot.services.team.TeamService;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.MessageSender;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.send.SendPhoto;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.Comparator;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * This class stores business logic for our bot.
 * All methods that start with reply define logic to execute in response to user input.
 * All reply method are public, unless specified otherwise.
 * Then it uses sender interface to send data back to the user.
 * favouriteTeams map stores favourite teams for each user.
 */

@Slf4j
@Setter
public class ResponseHandler {
    private final MessageSender sender;
    private final Map<Long, String> favouriteTeams;

    private final TeamService teamService;
    private final ScorerService scorerService;
    private final AssistService assistService;
    private final DataLoader dataLoader;

    public ResponseHandler(MessageSender sender, DBContext db, DataLoader dataLoader
            , TeamService teamService, ScorerService scorerService, AssistService assistService) {
        this.sender = sender;
        favouriteTeams = db.getMap(BotConfig.DB_FAVOURITE_TEAMS);
        this.dataLoader = dataLoader;
        this.teamService = teamService;
        this.scorerService = scorerService;
        this.assistService = assistService;
    }

    public void replyToStart(Long chatId) {
        try {
            sender.execute(new SendMessage()
                    .setText(BotConfig.START_MESSAGE)
                    .setChatId(chatId));
            SendPhoto message = new SendPhoto().setChatId(chatId)
                    .setPhoto("https://i.redd.it/zq5ch5o7z1f51.jpg");

            sender.sendPhoto(message);

           replyToMenu(chatId);

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void replyToMenu(Long chatId) {

        try {
            sender.execute(new SendMessage()
                    .setText(BotConfig.MAIN_MENU_MESSAGE)
                    .setChatId(chatId)
                    .setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId))));

        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    public void replyToButtons(Long chatId, String data) {
            switch (data) {
                case BotConfig.MENU_TABLE:
                    replyToTable(chatId);
                    break;
                case BotConfig.MENU_TOP_SCORERS:
                    replyToTopScorers(chatId);
                    break;
                case BotConfig.MENU_TOP_ASSISTS:
                    replyToTopAssists(chatId);
                    break;
                case BotConfig.MENU_REMOVE_TEAM:
                    removeFavouriteTeam(chatId);
                    break;
                case BotConfig.MENU_SET_TEAM:
                    replyToSetTeam(chatId);
                    break;
                case BotConfig.CREATOR_MENU_RELOAD_ALL:
                    dataLoader.loadAll(chatId);
                    sendNumberOfReloadsLeft(chatId);
                    break;
                case BotConfig.CREATOR_MENU_RELOAD_ASSISTS:
                    dataLoader.loadAssists(chatId);
                    sendNumberOfReloadsLeft(chatId);
                    break;
                case BotConfig.CREATOR_MENU_RELOAD_SCORERS:
                    dataLoader.loadScorers(chatId);
                    sendNumberOfReloadsLeft(chatId);
                    break;
                case BotConfig.CREATOR_MENU_RELOAD_TABLE:
                    dataLoader.loadTable(chatId);
                    sendNumberOfReloadsLeft(chatId);
                    break;
                default:
                    setFavouriteTeam(chatId, data);
                    replyToTeam(chatId);
            }
    }

    public void replyToTopAssists(Long chatId)  {
        StringBuilder message = new StringBuilder("Premier League Top Assists - 2020/2021\n\n");
        log.debug("Number of assists: " + assistService.getAllAssists().size());
        assistService.getAllAssists().stream()
                .sorted(Comparator.comparingInt(Assist::getPlace))               //sort the table
                .forEach(assist -> message.append(assist.toString() + "\n"));
        try {
            sender.execute(new SendMessage()
                    .setText(message.toString())
                    .setChatId(chatId)
                    .setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId))));
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }



    public void replyToTopScorers(Long chatId) {
        StringBuilder message = new StringBuilder("Premier League Top Scorers - 2020/2021\n\n");

        log.debug("Number of scorers: " + scorerService.getAllScorers().size());
        scorerService.getAllScorers().stream()
                .sorted(Comparator.comparingInt(Scorer::getPlace))               //sort the table
                .forEach(scorer -> message.append(scorer.toString() + "\n"));

        try{
            sender.execute(new SendMessage()
                    .setText(message.toString())
                    .setChatId(chatId)
                    .setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId))));
        } catch (TelegramApiException e){
        e.printStackTrace();
        }
    }

    public void replyToTable(Long chatId) {
        StringBuilder message = new StringBuilder("Premier League table - season 2020/2021\n\n");

        log.debug("Number of teams: " + teamService.getAllTeams().size());
        teamService.getAllTeams().stream()
                .sorted(Comparator.comparingInt(Team::getPlace))               //sort the table
                .forEach(team -> message.append(team.toString() + "\n"));

        message.append("P - points\nM - matches played\nW - wins\nD - draws\n" +
                "L - losses\nGS - goals scored\nGC - goals conceded");

        try{
            sender.execute(new SendMessage()
                    .setText(message.toString())
                    .setChatId(chatId)
                    .setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId))));
        } catch (TelegramApiException e){
        e.printStackTrace();
        }
    }



     private void setFavouriteTeam(Long chatId, String teamName){

        if (teamService.getTeamByName(teamName)!= null){
            favouriteTeams.put(chatId, teamName);
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
                    .setChatId(chatId)
                    .setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId))));

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
                message.setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId)));
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
                    .setText("Choose a team:" )
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

    /**
     * Command triggers data reload. Only available to the Creator user.
     * @param chatId - chatId of the user requested the action
     * @param creatorId - used to ensure user requested the action is the Creator
     */

    public void replyToLoadData(Long chatId, int creatorId) {
        //only creator is allowed to reload data
        if (chatId!= creatorId){
            return;
        }
        try {
            sender.execute(new SendMessage()
                    .setText("What would you like to reload?")
                    .setChatId(chatId)
                    .setReplyMarkup(KeyboardFactory.loadDataOptionsList()));
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }

    /**
     * REST API has a limited number of request per month.
     * This Creator only command sends number of requests left in the current month.
     * @param chatId
     */

    public void sendNumberOfReloadsLeft(Long chatId) {

        try {
            sender.execute(new SendMessage()
                    .setText("Number of reloads left this month: " + dataLoader.getNumberOfReloadsLeft())
                    .setChatId(chatId)
                    .setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId))));
        } catch (TelegramApiException e){
            e.printStackTrace();
        }
    }

    /**
     *
     * @param chatId -key used to search for the team in our map
     * @return team name for the current user or null if it is not found
     */

    private String getTeamName(Long chatId){
        return favouriteTeams.getOrDefault(chatId, null);
    }
}
