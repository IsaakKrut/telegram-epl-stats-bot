package com.isaakkrut.telegram.bots.premierleaguebot.services.jpa;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Assist;
import com.isaakkrut.telegram.bots.premierleaguebot.repositories.AssistRepository;
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
class AssistServiceJpaTest {

    @Mock
    AssistRepository assistRepository;

    @InjectMocks
    AssistServiceJpa assistService;

    Assist mockAssist;

    List<Assist> mockAssists;

    @BeforeEach
    void setUp() {
        mockAssist = Assist.builder()
                .id(1L)
                .place(1)
                .assists(8)
                .playerName("Harry Kane")
                .team("Tottenham")
                .build();

        mockAssists = new ArrayList<>();
        mockAssists.add(mockAssist);
    }

    @Test
    void getAllAssists() {
        when(assistRepository.findAll()).thenReturn(mockAssists);

        List<Assist> returnedAssists = assistService.getAllAssists();

        assertEquals(1, returnedAssists.size());
        verify(assistRepository, times(1)).findAll();
    }

    @Test
    void saveAll() {
        assistService.saveAll(mockAssists);

        verify(assistRepository, times(1)).saveAll(anyIterable());
    }

    @Test
    void deleteAll() {
        assistService.deleteAll();

        verify(assistRepository, times(1)).deleteAll();
    }
}