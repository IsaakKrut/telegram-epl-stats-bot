package com.isaakkrut.telegram.bots.premierleaguebot.services;

public interface DataLoader {
    void loadAll(Long chatId);
    void loadAssists(Long chatId);
    void loadTable(Long chatId);
    void loadScorers(Long chatId);
    int getNumberOfReloadsLeft();
}
