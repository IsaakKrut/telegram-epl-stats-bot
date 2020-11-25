package com.isaakkrut.telegram.bots.premierleaguebot.services.rest;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;

import java.util.List;
import java.util.Optional;

public interface TeamRestServiсe {
    Optional<List<Team>> getTeams();
}
