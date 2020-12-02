package com.isaakkrut.telegram.bots.premierleaguebot.services;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Assist;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Scorer;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;
import com.isaakkrut.telegram.bots.premierleaguebot.repositories.UpdateLogRepository;
import com.isaakkrut.telegram.bots.premierleaguebot.services.rest.RestServiсe;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DataLoaderImplTest {

    @Mock
    RestServiсe restServiсe;
    @Mock
    TeamService teamService;
    @Mock
    ScorerService scorerService;
    @Mock
    AssistService assistService;
    @Mock
    UpdateLogRepository updateLogRepository;

    @InjectMocks
    DataLoaderImpl dataLoader;

    List<Team> teams;
    List<Assist> assists;
    List<Scorer> scorers;

    public DataLoaderImplTest() {
        teams = new ArrayList<>();
        teams.add(Team.builder()
            .id(1L)
            .place(1)
            .team("Liverpool")
            .build());
        teams.add(Team.builder()
            .id(2L)
            .place(2)
            .team("Manchester City")
            .build());


        assists = new ArrayList<>();
        assists.add(Assist.builder()
            .id(1L)
            .place(1)
            .playerName("Harry Kane")
            .assists(8)
            .team("Tottenham")
            .build());
        assists.add(Assist.builder()
            .id(2L)
            .place(2)
            .playerName("Kevin De Bruyne")
            .assists(7)
            .team("Manchester City")
            .build());

        scorers = new ArrayList<>();
        scorers.add(Scorer.builder()
            .id(1L)
            .place(1)
            .goals(10)
            .playerName("Dominic Calwert-Lewin")
            .build());
        scorers.add(Scorer.builder()
                .id(2L)
                .place(2)
                .goals(9)
                .playerName("Son Heung-min")
                .build());
    }

    @Test
    void loadAll() {
        when(updateLogRepository.count()).thenReturn(50L);
        when(restServiсe.getTeams()).thenReturn(Optional.of(teams));
        when(restServiсe.getAssists()).thenReturn(Optional.of(assists));
        when(restServiсe.getScorers()).thenReturn(Optional.of(scorers));

        dataLoader.loadAll(1L);

        verify(updateLogRepository, times(3)).save(any());
        verify(teamService, times(1)).saveAll(any());
        verify(assistService, times(1)).saveAll(any());
        verify(scorerService, times(1)).saveAll(any());
        verify(restServiсe, times(1)).getTeams();
        verify(restServiсe, times(1)).getAssists();
        verify(restServiсe, times(1)).getScorers();
    }

    @Test
    void loadTable() {
        when(updateLogRepository.count()).thenReturn(50L);
        when(restServiсe.getTeams()).thenReturn(Optional.of(teams));

        dataLoader.loadTable(1L);

        verify(restServiсe, times(1)).getTeams();
        verify(teamService, times(1)).saveAll(any());
        verify(updateLogRepository, times(1)).save(any());
    }

    @Test
    void loadScorers() {

        when(updateLogRepository.count()).thenReturn(50L);
        when(restServiсe.getScorers()).thenReturn(Optional.of(scorers));

        dataLoader.loadScorers(1L);

        verify(restServiсe, times(1)).getScorers();
        verify(scorerService, times(1)).saveAll(any());
        verify(updateLogRepository, times(1)).save(any());
    }


    @Test
    void loadAssists() {

        when(updateLogRepository.count()).thenReturn(50L);
        when(restServiсe.getAssists()).thenReturn(Optional.of(assists));

        dataLoader.loadAssists(1L);

        verify(restServiсe, times(1)).getAssists();
        verify(assistService, times(1)).saveAll(any());
        verify(updateLogRepository, times(1)).save(any());
    }


    @Test
    void getNumberOfReloadsLeft() {
        when(updateLogRepository.count()).thenReturn(0L);

        int returnedUpdatesLeft = dataLoader.getNumberOfReloadsLeft();

        assertEquals(DataLoaderImpl.UPDATES_LIMIT, returnedUpdatesLeft);

    }
}