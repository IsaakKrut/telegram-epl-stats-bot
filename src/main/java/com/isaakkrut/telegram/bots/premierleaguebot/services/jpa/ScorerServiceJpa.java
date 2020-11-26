package com.isaakkrut.telegram.bots.premierleaguebot.services.jpa;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Scorer;
import com.isaakkrut.telegram.bots.premierleaguebot.repositories.ScorerRepository;
import com.isaakkrut.telegram.bots.premierleaguebot.services.ScorerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Service
public class ScorerServiceJpa implements ScorerService {
    private final ScorerRepository scorerRepository;

    @Cacheable(cacheNames = "scorersCache")
    @Override
    public List<Scorer> getAllScorers() {
        List<Scorer> scorers = new ArrayList<>();
        scorerRepository.findAll().forEach(scorers::add);
        return scorers;
    }

    @Override
    public void saveAll(List<Scorer> scorers) {
        scorerRepository.saveAll(scorers);
        log.debug(scorerRepository.count() + " scorers saved");
    }

    @Override
    public void deleteAll() {
        scorerRepository.deleteAll();
    }
}
