package com.isaakkrut.telegram.bots.premierleaguebot.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "team",
        "played",
        "win",
        "draw",
        "loss",
        "goalsFor",
        "goalsAgainst",
        "points"
})
public class TeamDto {
    @JsonProperty("team")
    private String team;
    @JsonProperty("played")
    private Integer played;
    @JsonProperty("win")
    private Integer win;
    @JsonProperty("draw")
    private Integer draw;
    @JsonProperty("loss")
    private Integer loss;
    @JsonProperty("goalsFor")
    private Integer goalsFor;
    @JsonProperty("goalsAgainst")
    private Integer goalsAgainst;
    @JsonProperty("points")
    private Integer points;

    @JsonProperty("team")
    public String getTeam() {
        return team;
    }

    @JsonProperty("team")
    public void setTeam(String team) {
        this.team = team;
    }

    @JsonProperty("played")
    public Integer getPlayed() {
        return played;
    }

    @JsonProperty("played")
    public void setPlayed(Integer played) {
        this.played = played;
    }

    @JsonProperty("win")
    public Integer getWin() {
        return win;
    }

    @JsonProperty("win")
    public void setWin(Integer win) {
        this.win = win;
    }

    @JsonProperty("draw")
    public Integer getDraw() {
        return draw;
    }

    @JsonProperty("draw")
    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    @JsonProperty("loss")
    public Integer getLoss() {
        return loss;
    }

    @JsonProperty("loss")
    public void setLoss(Integer loss) {
        this.loss = loss;
    }

    @JsonProperty("goalsFor")
    public Integer getGoalsFor() {
        return goalsFor;
    }

    @JsonProperty("goalsFor")
    public void setGoalsFor(Integer goalsFor) {
        this.goalsFor = goalsFor;
    }

    @JsonProperty("goalsAgainst")
    public Integer getGoalsAgainst() {
        return goalsAgainst;
    }

    @JsonProperty("goalsAgainst")
    public void setGoalsAgainst(Integer goalsAgainst) {
        this.goalsAgainst = goalsAgainst;
    }

    @JsonProperty("points")
    public Integer getPoints() {
        return points;
    }

    @JsonProperty("points")
    public void setPoints(Integer points) {
        this.points = points;
    }


}
