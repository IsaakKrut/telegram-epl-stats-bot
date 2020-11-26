package com.isaakkrut.telegram.bots.premierleaguebot.services;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.UpdateLog;
import com.isaakkrut.telegram.bots.premierleaguebot.repositories.ScorerRepository;
import com.isaakkrut.telegram.bots.premierleaguebot.repositories.TeamRepository;
import com.isaakkrut.telegram.bots.premierleaguebot.repositories.UpdateLogRepository;
import com.isaakkrut.telegram.bots.premierleaguebot.services.rest.RestServiсe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

@Slf4j
@RequiredArgsConstructor
@Service
public class DataLoaderImpl implements DataLoader {
    private final RestServiсe restServiсe;
    private final TeamRepository teamRepository;
    private final ScorerRepository scorerRepository;
    private final UpdateLogRepository updateLogRepository;
    private static final int  UPDATES_LIMIT = 50;


    //returns number of updates left this month
    //50 per month is a limit set by REST API
    @Override
    public int loadData(Long chatId) {
        updateLogRepository.save(UpdateLog.builder()
                .userIssuedUpdate(chatId)
                .updateTime(new Date())
                .build());
        System.out.println(updateLogRepository.count() + " - number of updates this month");

        teamRepository.deleteAll();
        if (teamRepository.count() == 0){
            restServiсe.getTeams().ifPresentOrElse(teams -> {
                        teams.forEach(teamRepository::save);
                    },
                    ()->log.debug("No teams returned from the rest service"));
        }

        scorerRepository.deleteAll();
        if (scorerRepository.count() == 0){
            restServiсe.getScorers().ifPresentOrElse(scorers -> {
                scorers.forEach(scorerRepository::save);
            }, ()->log.debug("No scorers returned from the rest service"));
        }
        log.debug("Number of teams: " + teamRepository.count());
        log.debug("Number of scorers: " + scorerRepository.count());
        return DataLoaderImpl.UPDATES_LIMIT - (int) updateLogRepository.count();

    }
}
