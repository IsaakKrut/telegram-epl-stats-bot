package com.isaakkrut.telegram.bots.premierleaguebot.services.responsehandlers;

import com.isaakkrut.telegram.bots.premierleaguebot.config.BotConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.methods.BotApiMethod;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.objects.CallbackQuery;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.Update;


@RequiredArgsConstructor
@Component
public class WebhookResponseHandler {

    private final ResponseProvider responseProvider;

    /**
     * This method receives updates from the bot and sends it down the flow based on whether the update is a
     * command (message starting with '/') or a callback query(pressed button). It returns a reply to the user
     * @param update
     * @return
     */
    public BotApiMethod handleUpdate(Update update) {

        if (update.hasCallbackQuery()){
            return processCallbackQuery(update.getCallbackQuery());
        }

        Message message = update.getMessage();

        if (message != null && message.hasText() && message.getText().startsWith("/")){
            return processCommand(update);
        }

        return null;
    }

    /**
     * This method get called if an update from the user is a command.
     * It returns a reply to the user based on the command sent
     * @param update
     * @return
     */
    private BotApiMethod processCommand(Update update) {
        String command = update.getMessage().getText();
        Long chatId = update.getMessage().getChatId();
        switch (command){
            case "/start":
                return responseProvider.replyToStart(chatId);
            case "/menu":
                return responseProvider.replyToMenu(chatId);
            case "/table":
                return responseProvider.replyToTable(chatId);
            case "/topassists":
                return responseProvider.replyToTopAssists(chatId);
            case "/topscorers":
                return responseProvider.replyToTopScorers(chatId);
            case "/setteam":
                return responseProvider.replyToSetTeam(chatId);
            case "/removeteam":
                return responseProvider.replyToRemoveTeam(chatId);
            case "/team":
                return responseProvider.replyToTeam(chatId);
            case "/loaddata":
                return responseProvider.replyToLoadData(chatId);
            default:
                return new SendMessage().setChatId(chatId)
                        .setText("Command is not available");
        }
    }

    /**
     * This method get called if an update from the user is Callback Query(button pressed by the user).
     * It returns a reply to the user based on the button pressed
     * @param callbackQuery
     * @return
     */
    private BotApiMethod processCallbackQuery(CallbackQuery callbackQuery) {
        String data = callbackQuery.getData();
        Long chatId = callbackQuery.getMessage().getChatId();

        switch (data) {
            case BotConfig.MENU_TABLE:
                return responseProvider.replyToTable(chatId);
            case BotConfig.MENU_TOP_SCORERS:
                return responseProvider.replyToTopScorers(chatId);
            case BotConfig.MENU_TOP_ASSISTS:
                return responseProvider.replyToTopAssists(chatId);
            case BotConfig.MENU_REMOVE_TEAM:
                return responseProvider.replyToRemoveTeam(chatId);
            case BotConfig.MENU_SET_TEAM:
                return responseProvider.replyToSetTeam(chatId);
            case BotConfig.CREATOR_MENU_RELOAD_ALL:
                return responseProvider.loadAll(chatId);
            case BotConfig.CREATOR_MENU_RELOAD_ASSISTS:
                return responseProvider.loadAssists(chatId);
            case BotConfig.CREATOR_MENU_RELOAD_SCORERS:
                return responseProvider.loadScorers(chatId);
            case BotConfig.CREATOR_MENU_RELOAD_TABLE:
                return responseProvider.loadTable(chatId);
            default:
                return responseProvider.setFavouriteTeam(chatId, data);
        }
    }
}
