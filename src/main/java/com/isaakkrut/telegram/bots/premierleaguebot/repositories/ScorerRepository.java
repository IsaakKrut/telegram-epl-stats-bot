package com.isaakkrut.telegram.bots.premierleaguebot.repositories;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Scorer;
import org.springframework.data.repository.CrudRepository;

public interface ScorerRepository extends CrudRepository<Scorer, Long> {
}
