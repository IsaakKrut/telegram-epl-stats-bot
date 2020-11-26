package com.isaakkrut.telegram.bots.premierleaguebot.mappers;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Scorer;
import com.isaakkrut.telegram.bots.premierleaguebot.model.ScorerDto;

public class ScorerMapper {

    public static Scorer scorerDtoToScorer(ScorerDto dto){
        return Scorer.builder()
                .goals(dto.getGoals())
                .penalties(dto.getPenalties())
                .playerName(dto.getPlayerName())
                .build();

    }
}
