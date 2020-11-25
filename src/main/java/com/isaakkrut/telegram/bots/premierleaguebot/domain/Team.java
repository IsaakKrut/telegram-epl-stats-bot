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
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Team implements Serializable {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private Long id;

    private int place;
    private String team;
    private int played;
    private int win;
    private int draw;
    private int loss;
    private int goalsFor;
    private int goalsAgainst;
    private int points;

    @Override
    public String toString() {
        return String.format("%d. %s\n" +
                        "P  M  W  D  L  GS  GC\n" +
                        "%-3d%-3d%-3d%-3d%-3d%-4d%-4d\n", place,
                        team, points, played, win, draw, loss, goalsFor, goalsAgainst );

    }

   /* Sample Json:
            {"team":"Manchester City"
            "played":10
            "win":8
            "draw":0
            "loss":2
            "goalsFor":29
            "goalsAgainst":12
            "points":24
            }*/

}
