package com.isaakkrut.telegram.bots.premierleaguebot.services;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.User;

public interface UserService {

    User saveFavouriteTeam(User user);

    User saveFavouriteTeam(Long chatId, String teamName);

    String getFavouriteTeamForUser(Long chatId);

    void removeFavouriteTeamForUser(Long chatId);

}
