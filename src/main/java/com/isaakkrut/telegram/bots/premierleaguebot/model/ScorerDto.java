package com.isaakkrut.telegram.bots.premierleaguebot.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "playerName",
        "goals",
        "penalties"
})
public class ScorerDto {
    @JsonProperty("playerName")
    private String playerName;
    @JsonProperty("goals")
    private Integer goals;
    @JsonProperty("penalties")
    private Integer penalties;

    @JsonProperty("playerName")
    public String getPlayerName() {
        return playerName;
    }

    @JsonProperty("playerName")
    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    @JsonProperty("goals")
    public Integer getGoals() {
        return goals;
    }

    @JsonProperty("goals")
    public void setGoals(Integer goals) {
        this.goals = goals;
    }

    @JsonProperty("penalties")
    public Integer getPenalties() {
        return penalties;
    }

    @JsonProperty("penalties")
    public void setPenalties(Integer penalties) {
        this.penalties = penalties;
    }

}
