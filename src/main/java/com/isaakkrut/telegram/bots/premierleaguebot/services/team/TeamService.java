package com.isaakkrut.telegram.bots.premierleaguebot.services.team;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;

import java.util.List;
import java.util.Optional;

public interface TeamService {

    List<Team> getAllTeams();

    Team getTeamByName(String name);
}
