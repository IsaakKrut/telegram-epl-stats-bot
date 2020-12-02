package com.isaakkrut.telegram.bots.premierleaguebot.services.jpa;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Scorer;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;
import com.isaakkrut.telegram.bots.premierleaguebot.repositories.ScorerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ScorerServiceJpaTest {

    @Mock
    ScorerRepository scorerRepository;

    @InjectMocks
    ScorerServiceJpa scorerService;

    Scorer mockScorer;

    List<Scorer> mockScorers;

    @BeforeEach
    void setUp() {
        mockScorer = Scorer.builder()
                .id(1L)
                .goals(10)
                .penalties(4)
                .place(1)
                .playerName("Harry Kane")
                .build();

        mockScorers = new ArrayList<>();
        mockScorers.add(mockScorer);
    }

    @Test
    void getAllScorers() {
        when(scorerRepository.findAll()).thenReturn(mockScorers);

        List<Scorer> returnedScorers = scorerService.getAllScorers();

        assertEquals(1, returnedScorers.size());
        verify(scorerRepository, times(1)).findAll();
    }

    @Test
    void saveAll() {
        scorerService.saveAll(mockScorers);

        verify(scorerRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void deleteAll() {
        scorerService.deleteAll();

        verify(scorerRepository, times(1)).deleteAll();
    }
}