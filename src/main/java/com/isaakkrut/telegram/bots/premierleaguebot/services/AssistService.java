package com.isaakkrut.telegram.bots.premierleaguebot.services;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Assist;

import java.util.List;

public interface AssistService {
    List<Assist> getAllAssists();

    void saveAll(List<Assist> assists);

    void deleteAll();
}
