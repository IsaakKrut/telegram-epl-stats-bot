package com.isaakkrut.telegram.bots.premierleaguebot.services;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.UpdateLog;
import com.isaakkrut.telegram.bots.premierleaguebot.repositories.UpdateLogRepository;
import com.isaakkrut.telegram.bots.premierleaguebot.services.rest.RestServiсe;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * This class is an abstraction to RestService.
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
    public static final int  UPDATES_LIMIT = 50;


    /**
     * This method asks RestService for new data in all categories and saves it to corresponding repositories.
     * It takes chatId to save the user who invoked the update.
     * @param chatId
     */
    @Override
    public void loadAll(Long chatId) {
        loadTable(chatId);
        loadScorers(chatId);
        loadAssists(chatId);
    }

    /**
     * This method asks RestService for new table data and saves it to the corresponding repository.
     * It takes chatId to save the user who invoked the update.
     * @param chatId
     */

    @Override
    public void loadTable(Long chatId) {
        updateLogRepository.save(UpdateLog.builder()
                .userIssuedUpdate(chatId)
                .updateTime(new Date())
                .build());
        log.debug(updateLogRepository.count() + " - number of updates this month");

        teamService.deleteAll();
            restServiсe.getTeams().ifPresentOrElse(teams-> teamService.saveAll(teams),
                    ()->log.debug("No teams returned from the rest service"));
    }

    /**
     * This method asks RestService for new scorers data and saves it to the corresponding repository.
     * It takes chatId to save the user who invoked the update.
     * @param chatId
     */

    @Override
    public void loadScorers(Long chatId) {
        updateLogRepository.save(UpdateLog.builder()
                .userIssuedUpdate(chatId)
                .updateTime(new Date())
                .build());
        log.debug(updateLogRepository.count() + " - number of updates this month");

        scorerService.deleteAll();
            restServiсe.getScorers().ifPresentOrElse(scorers -> scorerService.saveAll(scorers),
                    ()->log.debug("No scorers returned from the rest service"));

    }

    /**
     * This method asks RestService for new assists data and saves it to the corresponding repository.
     * It takes chatId to save the user who invoked the update.
     * @param chatId
     */
    @Override
    public void loadAssists(Long chatId) {
        updateLogRepository.save(UpdateLog.builder()
                .userIssuedUpdate(chatId)
                .updateTime(new Date())
                .build());
        log.debug(updateLogRepository.count() + " - number of updates this month");

        assistService.deleteAll();
        restServiсe.getAssists().ifPresentOrElse(assists ->assistService.saveAll(assists),
                ()->log.debug("No assists returned from the rest service"));
    }

    /**
     * this method returns number of reloads left based on the specified limit and number of updates invoked this month.
     * @return
     */
    @Override
    public int getNumberOfReloadsLeft() {
        return UPDATES_LIMIT -(int) updateLogRepository.count();
    }
}
