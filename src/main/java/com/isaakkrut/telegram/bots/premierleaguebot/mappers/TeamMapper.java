package com.isaakkrut.telegram.bots.premierleaguebot.mappers;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;
import com.isaakkrut.telegram.bots.premierleaguebot.model.TeamDto;
import org.springframework.stereotype.Component;

@Component
public class TeamMapper {

    public Team teamDtoToTeam(TeamDto dto){
        return Team.builder()
                .draw(dto.getDraw())
                .goalsAgainst(dto.getGoalsAgainst())
                .goalsFor(dto.getGoalsFor())
                .loss(dto.getLoss())
                .played(dto.getPlayed())
                .points(dto.getPoints())
                .team(dto.getTeam())
                .win(dto.getWin())
                .build();

    }
}
