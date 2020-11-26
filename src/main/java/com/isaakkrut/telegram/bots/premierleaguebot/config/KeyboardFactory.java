package com.isaakkrut.telegram.bots.premierleaguebot.config;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class KeyboardFactory {

    public static ReplyKeyboard mainMenu(String teamName) {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline2 = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText(BotConfig.MENU_TABLE).setCallbackData(BotConfig.MENU_TABLE));
        rowInline.add(new InlineKeyboardButton().setText(BotConfig.MENU_TOP_SCORERS).setCallbackData(BotConfig.MENU_TOP_SCORERS));
        rowInline.add(new InlineKeyboardButton().setText(BotConfig.MENU_TOP_ASSISTS).setCallbackData(BotConfig.MENU_TOP_ASSISTS));
        if (teamName == null){
            rowInline2.add(new InlineKeyboardButton().setText(BotConfig.MENU_SET_TEAM).setCallbackData(BotConfig.MENU_SET_TEAM));
        }else {
            rowInline2.add(new InlineKeyboardButton().setText(teamName).setCallbackData(teamName));
            rowInline2.add(new InlineKeyboardButton().setText(BotConfig.MENU_REMOVE_TEAM).setCallbackData(BotConfig.MENU_REMOVE_TEAM));
        }
        rowsInline.add(rowInline);
        rowsInline.add(rowInline2);
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }

    public static ReplyKeyboard teamsList(List<String> teams) {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        for (String teamName : teams){
            rowInline.add(new InlineKeyboardButton().setText(teamName).setCallbackData(teamName));
            if (rowInline.size() >= 2){    // limit to 4 buttons in 1 row
                rowsInline.add(rowInline);
                rowInline = new ArrayList<>();
            }
        }
        if (rowInline.size() > 0){
            rowsInline.add(rowInline);
        }
        inlineKeyboard.setKeyboard(rowsInline);
        return inlineKeyboard;
    }

    public static ReplyKeyboard loadDataOptionsList(){
            InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
            List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
            List<InlineKeyboardButton> rowInline = new ArrayList<>();

            rowInline.add(new InlineKeyboardButton().setText(BotConfig.CREATOR_MENU_RELOAD_TABLE)
                                                .setCallbackData(BotConfig.CREATOR_MENU_RELOAD_TABLE));
            rowInline.add(new InlineKeyboardButton().setText(BotConfig.CREATOR_MENU_RELOAD_SCORERS)
                                                .setCallbackData(BotConfig.CREATOR_MENU_RELOAD_SCORERS));
            rowInline.add(new InlineKeyboardButton().setText(BotConfig.CREATOR_MENU_RELOAD_ASSISTS)
                                                .setCallbackData(BotConfig.CREATOR_MENU_RELOAD_ASSISTS));


            List<InlineKeyboardButton> rowInline2 = new ArrayList<>();

            rowInline.add(new InlineKeyboardButton().setText(BotConfig.CREATOR_MENU_RELOAD_ALL)
                    .setCallbackData(BotConfig.CREATOR_MENU_RELOAD_ALL));

            rowsInline.add(rowInline);
            rowsInline.add(rowInline2);
            inlineKeyboard.setKeyboard(rowsInline);

            return inlineKeyboard;
    }
}
