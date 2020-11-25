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

    public static ReplyKeyboard mainMenu() {
        InlineKeyboardMarkup inlineKeyboard = new InlineKeyboardMarkup();
        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();
        rowInline.add(new InlineKeyboardButton().setText(BotConfig.TABLE).setCallbackData(BotConfig.TABLE));
        rowInline.add(new InlineKeyboardButton().setText(BotConfig.TOP_SCORERS).setCallbackData(BotConfig.TOP_SCORERS));
        rowInline.add(new InlineKeyboardButton().setText(BotConfig.TOP_ASSISTS).setCallbackData(BotConfig.TOP_ASSISTS));
        rowsInline.add(rowInline);
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
}
