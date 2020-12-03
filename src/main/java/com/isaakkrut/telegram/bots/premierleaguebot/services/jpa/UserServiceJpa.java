package com.isaakkrut.telegram.bots.premierleaguebot.services.jpa;

import com.isaakkrut.telegram.bots.premierleaguebot.domain.User;
import com.isaakkrut.telegram.bots.premierleaguebot.repositories.UserRepository;
import com.isaakkrut.telegram.bots.premierleaguebot.services.TeamService;
import com.isaakkrut.telegram.bots.premierleaguebot.services.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;


@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceJpa implements UserService {
    private final UserRepository userRepository;
    private final TeamService teamService;


    @Transactional
    @Override
    public User saveFavouriteTeam(User user) {
        if (userRepository.findByChatId(user.getChatId()).isPresent()) {
            userRepository.deleteByChatId(user.getChatId());
        }
        return userRepository.save(user);
    }


    @Transactional
    @Override
    public User saveFavouriteTeam(Long chatId, String teamName) {
        if (userRepository.findByChatId(chatId).isPresent()) {
            userRepository.deleteByChatId(chatId);
        }
        if (teamService.getTeamByName(teamName) != null){
            return userRepository.save(User.builder()
                    .chatId(chatId)
                    .teamName(teamName)
                    .build());
        }
        return null;
    }

    @Transactional
    @Override
    public String getFavouriteTeamForUser(Long chatId) {
        Optional<User> userOptional = userRepository.findByChatId(chatId);
        if (userOptional.isPresent()){
            return userOptional.get().getTeamName();
        }
        return null;
    }

    @Transactional
    @Override
    public void removeFavouriteTeamForUser(Long chatId) {
        userRepository.deleteByChatId(chatId);
    }

}
