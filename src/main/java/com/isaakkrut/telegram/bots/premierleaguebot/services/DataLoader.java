package com.isaakkrut.telegram.bots.premierleaguebot.services;

public interface DataLoader {
    int loadAll(Long chatId);
    int loadAssists(Long chatId);
    int loadTable(Long chatId);
    int loadScorers(Long chatId);
}
