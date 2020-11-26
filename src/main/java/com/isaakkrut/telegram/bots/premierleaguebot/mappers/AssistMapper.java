package com.isaakkrut.telegram.bots.premierleaguebot.mappers;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Assist;
import com.isaakkrut.telegram.bots.premierleaguebot.model.AssistDto;

public class AssistMapper {
    public static Assist assistDtoToAssist(AssistDto dto) {
        return Assist.builder()
                .assists(dto.getAssists())
                .playerName(dto.getPlayer())
                .team(dto.getTeam())
                .build();
    }
}
