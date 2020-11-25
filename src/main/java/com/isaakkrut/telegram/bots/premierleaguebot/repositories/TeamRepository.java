package com.isaakkrut.telegram.bots.premierleaguebot.repositories;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TeamRepository extends CrudRepository<Team, Long> {
    Optional<Team> findByTeam(String name);
}
