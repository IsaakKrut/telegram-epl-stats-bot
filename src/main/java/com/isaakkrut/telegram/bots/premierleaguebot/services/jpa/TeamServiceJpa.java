package com.isaakkrut.telegram.bots.premierleaguebot.services.jpa;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;
import com.isaakkrut.telegram.bots.premierleaguebot.repositories.TeamRepository;
import com.isaakkrut.telegram.bots.premierleaguebot.services.TeamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TeamServiceJpa implements TeamService {
    private final TeamRepository teamRepository;


    @Cacheable(cacheNames = "teamsCache")
    @Override
    public List<Team> getAllTeams() {
        List<Team> teams = new ArrayList<>();
        teamRepository.findAll().forEach(teams::add);
        return teams;
    }

    @Cacheable(cacheNames = "singleTeamCache", key = "#name")
    @Override
    public Team getTeamByName(String name) {
        return teamRepository.findByTeam(name).orElse(null);
    }

    @Override
    public void saveAll(List<Team> teams) {
        teamRepository.saveAll(teams);
        log.debug(teamRepository.count() + " teams saved");
    }

    @Override
    public void deleteAll() {
        teamRepository.deleteAll();
    }
}
