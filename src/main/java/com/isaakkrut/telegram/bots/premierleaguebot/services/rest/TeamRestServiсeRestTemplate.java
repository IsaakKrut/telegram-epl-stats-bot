
package com.isaakkrut.telegram.bots.premierleaguebot.services.rest;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Assist;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Scorer;
import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;
import com.isaakkrut.telegram.bots.premierleaguebot.mappers.AssistMapper;
import com.isaakkrut.telegram.bots.premierleaguebot.mappers.ScorerMapper;
import com.isaakkrut.telegram.bots.premierleaguebot.mappers.TeamMapper;
import com.isaakkrut.telegram.bots.premierleaguebot.model.AssistListDto;
import com.isaakkrut.telegram.bots.premierleaguebot.model.ScorerListDto;
import com.isaakkrut.telegram.bots.premierleaguebot.model.TeamListDto;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Setter
@ConfigurationProperties(prefix = "rapidapi")
@RequiredArgsConstructor
@Slf4j
@Service
public class TeamRestServiсeRestTemplate implements RestServiсe {

    private String xRapidapiKey;
    private String xRapidapiHost;
    private String url;
    private String tableUri;
    private String scorersUri;
    private String assistsUri;

    private final RestTemplate restTemplate;

    @Override
    public Optional<List<Team>> getTeams() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", xRapidapiKey);
        headers.set("x-rapidapi-host", xRapidapiHost);

        HttpEntity httpEntity = new HttpEntity("body", headers);

        TeamListDto teamsDto = restTemplate.exchange(url + tableUri, HttpMethod.GET, httpEntity, TeamListDto.class)
                                            .getBody();

        //convert to Team objects and set current position in the table
        List<Team> teams = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(1);

        teamsDto.getRecords().forEach(dto -> {
            Team team = TeamMapper.teamDtoToTeam(dto);
            team.setPlace(count.getAndIncrement());
            teams.add(team);
        });

        return Optional.of(teams);
    }

    @Override
    public Optional<List<Scorer>> getScorers() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", xRapidapiKey);
        headers.set("x-rapidapi-host", xRapidapiHost);

        HttpEntity httpEntity = new HttpEntity("body", headers);

        ScorerListDto scorersDto = restTemplate.exchange(url + scorersUri, HttpMethod.GET, httpEntity, ScorerListDto.class)
                .getBody();


        //convert to Team objects and set current position in the table
        List<Scorer> scorers = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(1);

        scorersDto.getScorers().forEach(dto -> {
            Scorer scorer = ScorerMapper.scorerDtoToScorer(dto);
            scorer.setPlace(count.getAndIncrement());
            scorers.add(scorer);
        });
        return Optional.of(scorers);
    }

    @Override
    public Optional<List<Assist>> getAssists() {
        HttpHeaders headers = new HttpHeaders();
        headers.set("x-rapidapi-key", xRapidapiKey);
        headers.set("x-rapidapi-host", xRapidapiHost);

        HttpEntity httpEntity = new HttpEntity("body", headers);

        AssistListDto assistsDto = restTemplate.exchange(url + assistsUri, HttpMethod.GET, httpEntity, AssistListDto.class)
                .getBody();
        //convert to Assist objects and set current position in the table
        List<Assist> assists = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(1);

        assistsDto.getTablestat().forEach(dto -> {
            Assist assist = AssistMapper.assistDtoToAssist(dto);
            assist.setPlace(count.getAndIncrement());
            assists.add(assist);
        });
        return Optional.of(assists);
    }
}
