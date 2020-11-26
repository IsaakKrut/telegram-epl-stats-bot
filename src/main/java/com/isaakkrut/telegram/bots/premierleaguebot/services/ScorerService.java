package com.isaakkrut.telegram.bots.premierleaguebot.services;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Assist;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Scorer;

import java.util.List;

public interface ScorerService {

    List<Scorer> getAllScorers();

    void saveAll(List<Scorer> scorers);

    void deleteAll();
}
