
package com.isaakkrut.telegram.bots.premierleaguebot.services;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Assist;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Scorer;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;
import com.isaakkrut.telegram.bots.premierleaguebot.services.rest.RestServiсe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
@Service
public class TeamRestServiсeRestTemplate implements RestServiсe {

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

    @Override
    public Optional<List<Scorer>> getScorers() {
        return Optional.empty();
    }

    @Override
    public Optional<List<Assist>> getAssists() {
        return Optional.empty();
    }
}
