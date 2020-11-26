package com.isaakkrut.telegram.bots.premierleaguebot.domain;


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
public class Assist implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private String playerName;
    private String team;
    private int assists;
    private int place;

    @Override
    public String toString() {
        return String.format("%d. %s\n" +
                        "Assists: %d\n", place,
                playerName, assists);

    }
}
