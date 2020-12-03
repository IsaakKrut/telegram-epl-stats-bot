package com.isaakkrut.telegram.bots.premierleaguebot.repositories;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface UserRepository extends CrudRepository<User, Long> {
    Optional<User> findByChatId(Long chatId);
    void deleteByChatId(Long chatId);
}
