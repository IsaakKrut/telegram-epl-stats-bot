package com.isaakkrut.telegram.bots.premierleaguebot.services.jpa;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;
import com.isaakkrut.telegram.bots.premierleaguebot.repositories.TeamRepository;
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
class TeamServiceJpaTest {

    @Mock
    TeamRepository teamRepository;

    @InjectMocks
    TeamServiceJpa teamService;

    List<Team> mockTeams;

    Team mockTeam;

    @BeforeEach
    void setUp() {

        mockTeam = Team.builder()
                .team("Liverpool")
                .id(1L)
                .draw(2)
                .goalsAgainst(10)
                .goalsFor(30)
                .loss(1)
                .place(1)
                .played(10)
                .win(7)
                .points(23)
                .build();

        mockTeams = new ArrayList<>();
        mockTeams.add(mockTeam);
    }

    @Test
    void getAllTeams() {
        when(teamRepository.findAll()).thenReturn(mockTeams);

        List<Team> returnedTeams = teamService.getAllTeams();

        assertEquals(1, returnedTeams.size());
        verify(teamRepository, times(1)).findAll();
    }

    @Test
    void getTeamByName() {
        when(teamRepository.findByTeam(anyString())).thenReturn(Optional.of(mockTeam));

        Team returnedTeam = teamService.getTeamByName("Liverpool");

        assertEquals(returnedTeam.getTeam(), "Liverpool");
        verify(teamRepository, times(1)).findByTeam(anyString());
    }

    @Test
    void saveAll() {

        teamService.saveAll(mockTeams);

        verify(teamRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void deleteAll() {
        teamService.deleteAll();

        verify(teamRepository, times(1)).deleteAll();
    }
}