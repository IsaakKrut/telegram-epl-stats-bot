package com.isaakkrut.telegram.bots.premierleaguebot.services.jpa;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Assist;
import com.isaakkrut.telegram.bots.premierleaguebot.repositories.AssistRepository;
import com.isaakkrut.telegram.bots.premierleaguebot.services.AssistService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class AssistServiceJpa implements AssistService {

    private final AssistRepository assistRepository;

    @Cacheable(cacheNames = "assistsCache")
    @Override
    public List<Assist> getAllAssists() {
        List<Assist> assists = new ArrayList<>();
        assistRepository.findAll().forEach(assists::add);
        return assists;
    }

    @Override
    public void saveAll(List<Assist> assists) {
        assistRepository.saveAll(assists);
        log.debug(assistRepository.count() + " assists saved");
    }

    @Override
    public void deleteAll() {
        assistRepository.deleteAll();
    }
}
