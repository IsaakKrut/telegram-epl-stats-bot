package com.isaakkrut.telegram.bots.premierleaguebot.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "records"
})
public class TeamListDto {

    @JsonProperty("records")
    private List<TeamDto> teams = null;

    @JsonProperty("records")
    public List<TeamDto> getRecords() {
        return teams;
    }

    @JsonProperty("records")
    public void setRecords(List<TeamDto> records) {
        this.teams = records;
    }
}
