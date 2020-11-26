package com.isaakkrut.telegram.bots.premierleaguebot.model;

import com.fasterxml.jackson.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "scorers"
})

public class ScorerListDto {
    @JsonProperty("scorers")
    private List<ScorerDto> scorers = null;

    @JsonProperty("scorers")
    public List<ScorerDto> getScorers() {
        return scorers;
    }

    @JsonProperty("scorers")
    public void setScorers(List<ScorerDto> scorers) {
        this.scorers = scorers;
    }
}
