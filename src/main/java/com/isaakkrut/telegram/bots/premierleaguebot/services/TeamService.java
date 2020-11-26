package com.isaakkrut.telegram.bots.premierleaguebot.services;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;

import java.util.List;

public interface TeamService {

    List<Team> getAllTeams();

    Team getTeamByName(String name);
    
    void saveAll(List<Team> teams);

    void deleteAll();
}
