package com.isaakkrut.telegram.bots.premierleaguebot.services.scorer;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Scorer;
import com.isaakkrut.telegram.bots.premierleaguebot.repositories.ScorerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


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
}
