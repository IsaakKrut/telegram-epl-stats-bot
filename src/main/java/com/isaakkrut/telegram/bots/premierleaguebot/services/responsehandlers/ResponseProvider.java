package com.isaakkrut.telegram.bots.premierleaguebot.services.responsehandlers;

import com.isaakkrut.telegram.bots.premierleaguebot.config.BotConfig;
import com.isaakkrut.telegram.bots.premierleaguebot.config.KeyboardFactory;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Assist;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Scorer;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;
import com.isaakkrut.telegram.bots.premierleaguebot.services.AssistService;
import com.isaakkrut.telegram.bots.premierleaguebot.services.DataLoader;
import com.isaakkrut.telegram.bots.premierleaguebot.services.ScorerService;
import com.isaakkrut.telegram.bots.premierleaguebot.services.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.Update;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Component
public class ResponseProvider {
    private final TeamService teamService;
    private final ScorerService scorerService;
    private final AssistService assistService;
    private final DataLoader dataLoader;
    private final Map<Long, String > favouriteTeams = new HashMap<>();

    public BotApiMethod replyToStart(Long chatId) {

        return new SendMessage()
                .setText(BotConfig.START_MESSAGE)
                .setChatId(chatId)
                .setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId)));
    }

    public BotApiMethod replyToMenu(Long chatId) {

        return new SendMessage()
                .setText(BotConfig.MAIN_MENU_MESSAGE)
                .setChatId(chatId)
                .setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId)));
    }


    public BotApiMethod replyToTable(Long chatId) {
        SendMessage sendMessage = new SendMessage().setChatId(chatId);
        List<Team> teams = teamService.getAllTeams();

        if (teams.size() == 0){
            return sendMessage.setText("Database is empty");
        } else {
        StringBuilder message = new StringBuilder("Premier League table - season 2020/2021\n\n");

        log.debug("Number of teams: " + teams.size());

            teams.stream()
                    .sorted(Comparator.comparingInt(Team::getPlace))               //sort the table
                    .forEach(team -> message.append(team.toString() + "\n"));

            message.append("P - points\nM - matches played\nW - wins\nD - draws\n" +
                    "L - losses\nGS - goals scored\nGC - goals conceded");
            sendMessage.setText(message.toString());
        }

        return sendMessage.setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId)));
    }

    public BotApiMethod replyToTopAssists(Long chatId) {
        SendMessage sendMessage = new SendMessage().setChatId(chatId);
        List<Assist> assists = assistService.getAllAssists();

        if (assists.size() == 0){
            return sendMessage.setText("Database is empty");
        } else {
            StringBuilder message = new StringBuilder("Premier League Top Assists - season 2020/2021\n\n");

            log.debug("Number of assists: " + assists.size());

            assists.stream()
                    .sorted(Comparator.comparingInt(Assist::getPlace))               //sort the table
                    .forEach(assist -> message.append(assist.toString() + "\n"));

            sendMessage.setText(message.toString());
        }
        return sendMessage.setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId)));
    }

    public BotApiMethod replyToTopScorers(Long chatId) {
        SendMessage sendMessage = new SendMessage().setChatId(chatId);
        List<Scorer> scorers = scorerService.getAllScorers();

        if (scorers.size() == 0){
            return sendMessage.setText("Database is empty");
        } else {
            StringBuilder message = new StringBuilder("Premier League Top Scorers - season 2020/2021\n\n");

            log.debug("Number of teams: " + scorers.size());

            scorers.stream()
                    .sorted(Comparator.comparingInt(Scorer::getPlace))               //sort the table
                    .forEach(scorer -> message.append(scorer.toString() + "\n"));

            sendMessage.setText(message.toString());
        }

        return sendMessage.setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId)));
    }

    public BotApiMethod replyToLoadData(Long chatId) {
        return new SendMessage()
                .setChatId(chatId)
                .setText("What would you like to reload?")
                .setReplyMarkup(KeyboardFactory.loadDataOptionsList());
    }

    public BotApiMethod replyToTeam(Long chatId) {
        SendMessage message = new SendMessage().setChatId(chatId);
        if (favouriteTeams.containsKey(chatId)){

            String teamName = favouriteTeams.get(chatId);
            Team team = teamService.getTeamByName(teamName);

            if (team!= null){
                message.setText(team.toString());
            } else{
                message.setText("Team is not found."); // should not get here as we validate teams before setting them
            }
        } else {
            message.setText("Team has not been set yet");
        }

        return message.setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId)));
    }

    public BotApiMethod replyToRemoveTeam(Long chatId) {
        favouriteTeams.remove(chatId);
        return new SendMessage().setChatId(chatId)
                            .setText("Favourite Team has been successfully removed")
                            .setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId)));
    }

    public BotApiMethod replyToSetTeam(Long chatId) {
        List<Team> teams = teamService.getAllTeams();
        if (teams.size() > 0) {
            return new SendMessage()
                    .setText("Choose a team:")
                    .setChatId(chatId)
                    .setReplyMarkup(
                            KeyboardFactory.teamsList(teams.stream()
                                    .map(Team::getTeam)//get team names to set up the buttons
                                    .collect(Collectors.toList())));
        } else {
            return new SendMessage().setChatId(chatId)
                    .setText("Database is empty")
                    .setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId)));
        }
    }

    public BotApiMethod loadAll(Long chatId) {
        dataLoader.loadAll(chatId);
        return new SendMessage()
                .setText("Success! Number of reloads left this month: " + dataLoader.getNumberOfReloadsLeft())
                .setChatId(chatId)
                .setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId)));
    }

    public BotApiMethod loadAssists(Long chatId) {
        dataLoader.loadAssists(chatId);
        return new SendMessage()
                .setText("Success! Number of reloads left this month: " + dataLoader.getNumberOfReloadsLeft())
                .setChatId(chatId)
                .setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId)));
    }

    public BotApiMethod loadScorers(Long chatId) {
        dataLoader.loadScorers(chatId);
        return new SendMessage()
                .setText("Success! Number of reloads left this month: " + dataLoader.getNumberOfReloadsLeft())
                .setChatId(chatId)
                .setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId)));
    }

    public BotApiMethod loadTable(Long chatId) {
        dataLoader.loadTable(chatId);
        return new SendMessage()
                .setText("Success! Number of reloads left this month: " + dataLoader.getNumberOfReloadsLeft())
                .setChatId(chatId)
                .setReplyMarkup(KeyboardFactory.mainMenu(getTeamName(chatId)));
    }

    public BotApiMethod setFavouriteTeam(Long chatId, String data) {
        favouriteTeams.put(chatId, data);
        return replyToTeam(chatId);
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
