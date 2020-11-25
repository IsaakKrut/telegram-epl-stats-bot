/*
package com.isaakkrut.telegram.bots.premierleaguebot.services;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Profile("mockdata")
@Service
public class TeamRestServiсeRestTemplate implements TeamRestServiсe {

    @Override
    public Optional<List<Team>> getTeams() {
        List<Team> teams = new ArrayList<>();
        teams.add(Team.builder()
                .place(2)
                .team("Liverpool")
                .played(9)
                .win(6)
                .draw(2)
                .loss(1)
                .goalsFor(21)
                .goalsAgainst(16)
                .points(20)
                .build());
        teams.add(Team.builder()
                .place(1)
                .team("Tottenham Hotspur")
                .played(9)
                .win(6)
                .draw(2)
                .loss(1)
                .goalsFor(21)
                .goalsAgainst(9)
                .points(20)
                .build());
        teams.add(Team.builder()
                .place(11)
                .team("Manchester City")
                .played(8)
                .win(3)
                .draw(3)
                .loss(2)
                .goalsFor(10)
                .goalsAgainst(11)
                .points(12)
                .build());
        return Optional.of(teams);
    }
}
*/
