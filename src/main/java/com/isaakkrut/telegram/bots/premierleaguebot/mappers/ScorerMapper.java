package com.isaakkrut.telegram.bots.premierleaguebot.mappers;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Scorer;
import com.isaakkrut.telegram.bots.premierleaguebot.model.ScorerDto;
import org.springframework.stereotype.Component;

@Component
public class ScorerMapper {

    public Scorer scorerDtoToScorer(ScorerDto dto){
        return Scorer.builder()
                .goals(dto.getGoals())
                .penalties(dto.getPenalties())
                .playerName(dto.getPlayerName())
                .build();

    }
}
