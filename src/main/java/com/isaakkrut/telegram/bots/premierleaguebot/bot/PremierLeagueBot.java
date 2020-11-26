package com.isaakkrut.telegram.bots.premierleaguebot.bot;

import com.isaakkrut.telegram.bots.premierleaguebot.config.BotConfig;
import com.isaakkrut.telegram.bots.premierleaguebot.services.DataLoader;
import com.isaakkrut.telegram.bots.premierleaguebot.services.assist.AssistService;
import com.isaakkrut.telegram.bots.premierleaguebot.services.scorer.ScorerService;
import com.isaakkrut.telegram.bots.premierleaguebot.services.team.TeamService;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.api.objects.Update;

import java.util.function.Consumer;

import static org.telegram.abilitybots.api.objects.Locality.ALL;
import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.*;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

public class PremierLeagueBot extends AbilityBot {
    public static final org.telegram.abilitybots.api.objects.Privacy privacy = ADMIN;
    private int creatorId;
    private final ResponseHandler responseHandler;

    public PremierLeagueBot(String botToken, String botUsername, int creatorId, DataLoader dataLoader
            , TeamService teamService, ScorerService scorerService, AssistService assistService) {
        super(botToken, botUsername);
        this.creatorId = creatorId;
        responseHandler = new ResponseHandler(sender, db, dataLoader, teamService, scorerService, assistService);
    }


    public Ability replyToStart() {
        return Ability
                .builder()
                .name("start")
                .info(BotConfig.START_COMMAND_DESCRIPTION)
                .locality(ALL)
                .privacy(privacy)
                .action(ctx ->  responseHandler.replyToStart(ctx.chatId()))
                .build();
    }

    public Ability replyToMenu() {
        return Ability
                .builder()
                .name("menu")
                .info(BotConfig.MENU_COMMAND_DESCRIPTION)
                .locality(ALL)
                .privacy(privacy)
                .action(ctx ->  responseHandler.replyToMenu(ctx.chatId()))
                .build();
    }
    public Ability replyToLoadData() {
        return Ability
                .builder()
                .name("loaddata")
                .info("Reload data from rapidapi")
                .locality(USER)
                .privacy(CREATOR)
                .action(ctx ->  responseHandler.replyToLoadData(ctx.chatId(), this.creatorId))
                .build();
    }


    public Ability replyToTable() {
        return Ability
                .builder()
                .name("table")
                .info(BotConfig.TABLE_COMMAND_DESCRIPTION)
                .locality(ALL)
                .privacy(privacy)
                .action(ctx ->  responseHandler.replyToTable(ctx.chatId()))
                .build();
    }

    public Ability replyToTopScorers() {
        return Ability
                .builder()
                .name("topscorers")
                .info(BotConfig.TOP_SCORERS_COMMAND_DESCRIPTION)
                .locality(ALL)
                .privacy(privacy)
                .action(ctx ->  responseHandler.replyToTopScorers(ctx.chatId()))
                .build();
    }

    public Ability replyToTopAssists() {
        return Ability
                .builder()
                .name("topassists")
                .info(BotConfig.TOP_ASSISTS_COMMAND_DESCRIPTION)
                .locality(ALL)
                .privacy(privacy)
                .action(ctx ->  responseHandler.replyToTopAssists(ctx.chatId()))
                .build();
    }

    public Ability replyToTeam() {
        return Ability
                .builder()
                .name("team")
                .info(BotConfig.TEAM_COMMAND_DESCRIPTION)
                .locality(ALL)
                .privacy(privacy)
                .action(upd -> responseHandler.replyToTeam(upd.chatId()))
                .build();
    }

    public Ability replyToSetTeam() {
        return Ability
                .builder()
                .name("setteam")
                .info(BotConfig.SET_TEAM_COMMAND_DESCRIPTION)
                .locality(ALL)
                .privacy(privacy)
                .action(upd -> responseHandler.replyToSetTeam(upd.chatId()))
                .build();
    }
    public Ability replyToRemoveTeam() {
        return Ability
                .builder()
                .name("removeteam")
                .info(BotConfig.TEAM_COMMAND_DESCRIPTION)
                .locality(ALL)
                .privacy(privacy)
                .action(ctx -> responseHandler.removeFavouriteTeam(ctx.chatId()))
                .build();
    }

    public Ability replyToReloadsLeft() {
        return Ability
                .builder()
                .name("reloadsleft")
                .info("Number of calls to the api left this month")
                .locality(ALL)
                .privacy(CREATOR)
                .action(ctx -> responseHandler.sendNumberOfReloadsLeft(ctx.chatId()))
                .build();
    }


    public Reply replyToButtons() {
        Consumer<Update> action = upd -> responseHandler.replyToButtons(getChatId(upd), upd.getCallbackQuery().getData());
        return Reply.of(action, Flag.CALLBACK_QUERY);
    }



    @Override
    public int creatorId() {
        return this.creatorId;
    }

    /*
    @Override
    public Ability reportCommands() {
        return Ability
                .builder()
                .name("commands")
                .info(BotConfig.COMMANDS_COMMAND_DECRIPTION)
                .locality(ALL)
                .privacy(ADMIN)
                .action(ctx ->  responseHandler.replyToCommands(ctx.chatId()))
                .build();
    }*/
}
