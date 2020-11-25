package com.isaakkrut.telegram.bots.premierleaguebot.services.rest;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.Team;
import com.isaakkrut.telegram.bots.premierleaguebot.mappers.TeamMapper;
import com.isaakkrut.telegram.bots.premierleaguebot.model.TeamListDto;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Setter
@ConfigurationProperties(prefix = "rapidapi")
@Service
public class TeamRestServiсeWebClient implements TeamRestServiсe {
    private String xRapidapiKey;
    private String xRapidapiHost;
    private String url;
    private String tableUri;

    private WebClient webClient;

    public TeamRestServiсeWebClient(WebClient.Builder webClientBuilder){
        webClient = WebClient.create();
       // webClient = webClientBuilder.baseUrl("http://localhost:8080").build();
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

}
