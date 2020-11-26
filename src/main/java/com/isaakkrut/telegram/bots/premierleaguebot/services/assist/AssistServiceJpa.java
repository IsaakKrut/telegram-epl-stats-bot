package com.isaakkrut.telegram.bots.premierleaguebot.services.assist;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Assist;
import com.isaakkrut.telegram.bots.premierleaguebot.repositories.AssistRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class AssistServiceJpa implements AssistService {

    AssistRepository assistRepository;
    @Override
    public List<Assist> getAllAssists() {
        List<Assist> assists = new ArrayList<>();
        assistRepository.findAll().forEach(assists::add);
        return assists;
    }
}
