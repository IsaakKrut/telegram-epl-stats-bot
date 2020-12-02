package com.isaakkrut.telegram.bots.premierleaguebot.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "tablestat"
})

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssistListDto {
    @JsonProperty("tablestat")
    private List<AssistDto> tablestat = null;

    @JsonProperty("tablestat")
    public List<AssistDto> getTablestat() {
        return tablestat;
    }

    @JsonProperty("tablestat")
    public void setTablestat(List<AssistDto> tablestat) {
        this.tablestat = tablestat;
    }

}
