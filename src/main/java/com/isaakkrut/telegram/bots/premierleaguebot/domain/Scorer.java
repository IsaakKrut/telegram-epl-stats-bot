package com.isaakkrut.telegram.bots.premierleaguebot.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Scorer implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String playerName;
    private int goals;
    private int penalties;
    private int place;

    @Override
    public String toString() {
        return String.format("%d. %s\n" +
                        "Goals: %d\n" +
                        "Penalties: %d\n", place,
                playerName, goals, penalties );

    }
}
