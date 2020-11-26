package com.isaakkrut.telegram.bots.premierleaguebot.services.rest;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Scorer;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;

import java.util.List;
import java.util.Optional;

public interface RestServiсe {
    Optional<List<Team>> getTeams();
    Optional<List<Scorer>> getScorers();
}
