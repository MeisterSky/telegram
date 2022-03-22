package com.github.meistersky.telegram.service;

import com.github.meistersky.telegram.repository.UserGroupRepository;
import com.github.meistersky.telegram.repository.entity.TelegramUser;
import com.github.meistersky.telegram.repository.entity.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.ws.rs.NotFoundException;
import java.util.ArrayList;
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
        this.save(userGroup);
        return userGroup;
    }

    @Override
    public void save(UserGroup userGroup) {
        userGroupRepository.save(userGroup);
    }

    @Override
    public boolean isExistGroup(Long chatId, String title) {
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        for (UserGroup userGroups : userGroupList) {
            if (userGroups.getChatId().equals(chatId) && userGroups.getTitle().equalsIgnoreCase(title)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public List<UserGroup> findAllByChatId(Long chatId) {
        List<UserGroup> sourceUserGroupList = userGroupRepository.findAll();
        List<UserGroup> destUserGroupList = new ArrayList<>();
        for (UserGroup userGroups : sourceUserGroupList) {
            if (userGroups.getChatId().equals(chatId)) {
                destUserGroupList.add(userGroups);
            }
        }
        return destUserGroupList;
    }

    @Override
    public UserGroup findByTitle(Long chatId, String title) {
        List<UserGroup> userGroupList = userGroupRepository.findAll();
        for (UserGroup userGroups : userGroupList) {
            if (userGroups.getChatId().equals(chatId)
                    && userGroups.getTitle().trim().replaceAll("\s+", " ")
                    .equalsIgnoreCase(title.trim().replaceAll("\s+", " "))) {
                return userGroups;
            }
        }
        return null;
    }

    @Override
    public Optional<UserGroup> findById(Integer id) {
        return userGroupRepository.findById(id);
    }

    @Override
    public void delete(UserGroup userGroup) {
        userGroupRepository.delete(userGroup);
    }
}
