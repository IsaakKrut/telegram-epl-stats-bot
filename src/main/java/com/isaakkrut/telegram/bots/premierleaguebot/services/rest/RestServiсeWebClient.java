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
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * This implementation retrieves data from target REST API using WebClient
 */

@Setter
@ConfigurationProperties(prefix = "rapidapi")
@Service
public class RestServiсeWebClient implements RestServiсe {
    private String xRapidapiKey;
    private String xRapidapiHost;
    private String url;
    private String tableUri;
    private String scorersUri;
    private String assistsUri;

    private WebClient webClient;

    public RestServiсeWebClient(WebClient.Builder webClientBuilder){
        webClient = WebClient.create();
    }


    @Override
    public Optional<List<Team>> getTeams() {

       TeamListDto teamsDto = webClient.get()
                .uri(url + tableUri)
                .header("x-rapidapi-key", xRapidapiKey)
                .header("x-rapidapi-host", xRapidapiHost)
                .retrieve()
                .bodyToMono(TeamListDto.class)
                .block();

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
        ScorerListDto scorersDto = webClient.get()
                .uri(url + scorersUri)
                .header("x-rapidapi-key", xRapidapiKey)
                .header("x-rapidapi-host", xRapidapiHost)
                .retrieve()
                .bodyToMono(ScorerListDto.class)
                .block();

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
        AssistListDto assistSDto = webClient.get()
                .uri(url + assistsUri)
                .header("x-rapidapi-key", xRapidapiKey)
                .header("x-rapidapi-host", xRapidapiHost)
                .retrieve()
                .bodyToMono(AssistListDto.class)
                .block();

        //convert to Team objects and set current position in the table
        List<Assist> assists = new ArrayList<>();
        AtomicInteger count = new AtomicInteger(1);

        assistSDto.getTablestat().forEach(dto -> {
            Assist assist = AssistMapper.assistDtoToAssist(dto);
            assist.setPlace(count.getAndIncrement());
            assists.add(assist);
        });
        return Optional.of(assists);
    }

}
