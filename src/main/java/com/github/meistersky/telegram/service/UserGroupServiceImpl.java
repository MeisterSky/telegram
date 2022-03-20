package com.github.meistersky.telegram.service;

import com.github.meistersky.telegram.repository.UserGroupRepository;
import com.github.meistersky.telegram.repository.entity.TelegramUser;
import com.github.meistersky.telegram.repository.entity.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    private final TelegramUserService telegramUserService;
    private final UserGroupRepository userGroupRepository;

    @Autowired
    public UserGroupServiceImpl(TelegramUserService telegramUserService, UserGroupRepository userGroupRepository) {
        this.telegramUserService = telegramUserService;
        this.userGroupRepository = userGroupRepository;
    }

    @Override
    public UserGroup save(Long chatId, String title, String users) {
        TelegramUser telegramUser = telegramUserService.findByChatId(chatId).orElseThrow(NotFoundException::new);
        //TODO add exception handling
        UserGroup userGroup = new UserGroup();
        userGroup.setChatId(chatId);
        userGroup.setTitle(title);
        userGroup.setUsers(users);
        return userGroup;
    }

    @Override
    public UserGroup save(UserGroup userGroup) {
        return null;
    }

    @Override
    public Optional<UserGroup> findById(Integer id) {
        return Optional.empty();
    }

    @Override
    public List<UserGroup> findAll() {
        return null;
    }
}
