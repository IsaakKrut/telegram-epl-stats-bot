package com.isaakkrut.telegram.bots.premierleaguebot.services;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.UpdateLog;
import com.isaakkrut.telegram.bots.premierleaguebot.repositories.UpdateLogRepository;
import com.isaakkrut.telegram.bots.premierleaguebot.services.rest.RestServiсe;
import com.isaakkrut.telegram.bots.premierleaguebot.services.rest.RestServiсeWebClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * This class is an abstraction to the RestService.
 * It persists data returned by RestService
 */

@Slf4j
@RequiredArgsConstructor
@Service
public class DataLoaderImpl implements DataLoader {
    private final RestServiсe restServiсe;
    private final TeamService teamService;
    private final ScorerService scorerService;
    private final AssistService assistService;
    private final UpdateLogRepository updateLogRepository;
    private static final int  UPDATES_LIMIT = 50;


    @Override
    public void loadAll(Long chatId) {
        loadTable(chatId);
        loadScorers(chatId);
        loadAssists(chatId);
    }

    @Override
    public void loadTable(Long chatId) {
        updateLogRepository.save(UpdateLog.builder()
                .userIssuedUpdate(chatId)
                .updateTime(new Date())
                .build());
        System.out.println(updateLogRepository.count() + " - number of updates this month");

        teamService.deleteAll();
            restServiсe.getTeams().ifPresentOrElse(teams-> teamService.saveAll(teams),
                    ()->log.debug("No teams returned from the rest service"));
    }

    @Override
    public void loadScorers(Long chatId) {
        updateLogRepository.save(UpdateLog.builder()
                .userIssuedUpdate(chatId)
                .updateTime(new Date())
                .build());
        System.out.println(updateLogRepository.count() + " - number of updates this month");

        scorerService.deleteAll();
            restServiсe.getScorers().ifPresentOrElse(scorers -> scorerService.saveAll(scorers),
                    ()->log.debug("No scorers returned from the rest service"));

    }

    @Override
    public int getNumberOfReloadsLeft() {
        return UPDATES_LIMIT -(int) updateLogRepository.count();
    }

    @Override
    public void loadAssists(Long chatId) {
        updateLogRepository.save(UpdateLog.builder()
                .userIssuedUpdate(chatId)
                .updateTime(new Date())
                .build());
        System.out.println(updateLogRepository.count() + " - number of updates this month");

        assistService.deleteAll();
        restServiсe.getAssists().ifPresentOrElse(assists ->assistService.saveAll(assists),
                ()->log.debug("No assists returned from the rest service"));
    }
}
