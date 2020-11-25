package com.isaakkrut.telegram.bots.premierleaguebot.repositories;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.UpdateLog;
import org.springframework.data.repository.CrudRepository;

public interface UpdateLogRepository extends CrudRepository<UpdateLog, Long> {
}
