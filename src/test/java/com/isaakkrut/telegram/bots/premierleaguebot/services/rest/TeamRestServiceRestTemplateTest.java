package com.isaakkrut.telegram.bots.premierleaguebot.services.rest;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Assist;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Scorer;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;
import com.isaakkrut.telegram.bots.premierleaguebot.mappers.AssistMapper;
import com.isaakkrut.telegram.bots.premierleaguebot.mappers.ScorerMapper;
import com.isaakkrut.telegram.bots.premierleaguebot.mappers.TeamMapper;
import com.isaakkrut.telegram.bots.premierleaguebot.model.*;
import com.isaakkrut.telegram.bots.premierleaguebot.repositories.AssistRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TeamRestServiceRestTemplateTest {

    @Mock
    RestTemplate restTemplate;

    @Mock
    TeamMapper teamMapper;
    @Mock
    AssistMapper assistMapper;

    @Mock
    ScorerMapper scorerMapper;

    @InjectMocks
    TeamRestServiceRestTemplate restService;

    @BeforeEach
    void setUp() {
    }

    @Test
    void getTeams() {
        //given
        TeamDto teamDto = new TeamDto();
        List<TeamDto> teamDtoList = new ArrayList<TeamDto>();
        teamDtoList.add(teamDto);

        TeamListDto teamListDto = TeamListDto.builder()
                .teams(teamDtoList)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", restService.getXRapidapiKey());
        headers.set("x-rapidapi-host", restService.getXRapidapiHost());

        HttpEntity httpEntity = new HttpEntity("body", headers);

        //when
        when(teamMapper.teamDtoToTeam(any())).thenReturn(new Team());
        when(restTemplate.exchange(restService.getUrl() + restService.getTableUri()
                                        , HttpMethod.GET, httpEntity,TeamListDto.class))
                .thenReturn(new ResponseEntity<>(teamListDto, HttpStatus.OK));

        //then
        List<Team> returnedTeams = restService.getTeams().orElse(null);

        assertEquals(1, returnedTeams.size());
    }

    @Test
    void getScorers() {
        //given
        ScorerDto scorerDto = new ScorerDto();
        List<ScorerDto> scorerDtos = new ArrayList<>();
        scorerDtos.add(scorerDto);

        ScorerListDto scorerListDto = ScorerListDto.builder()
                .scorers(scorerDtos)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", restService.getXRapidapiKey());
        headers.set("x-rapidapi-host", restService.getXRapidapiHost());

        HttpEntity httpEntity = new HttpEntity("body", headers);

        //when
        when(scorerMapper.scorerDtoToScorer(any())).thenReturn(new Scorer());
        when(restTemplate.exchange(restService.getUrl() + restService.getScorersUri()
                , HttpMethod.GET, httpEntity,ScorerListDto.class))
                .thenReturn(new ResponseEntity<>(scorerListDto, HttpStatus.OK));

        //then
        List<Scorer> returnedScorers = restService.getScorers().orElse(null);

        assertEquals(1, returnedScorers.size());
    }

    @Test
    void getAssists() {
        //given
        AssistDto assistDto = new AssistDto();
        List<AssistDto> assistDtos = new ArrayList<>();
        assistDtos.add(assistDto);

        AssistListDto assistListDto = AssistListDto.builder()
                .tablestat(assistDtos)
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", restService.getXRapidapiKey());
        headers.set("x-rapidapi-host", restService.getXRapidapiHost());

        HttpEntity httpEntity = new HttpEntity("body", headers);

        //when
        when(assistMapper.assistDtoToAssist(any())).thenReturn(new Assist());
        when(restTemplate.exchange(restService.getUrl() + restService.getAssistsUri()
                , HttpMethod.GET, httpEntity, AssistListDto.class))
                .thenReturn(new ResponseEntity<>(assistListDto, HttpStatus.OK));

        //then
        List<Assist> returnedAssists = restService.getAssists().orElse(null);

        assertEquals(1, returnedAssists.size());
    }
}