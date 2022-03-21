package com.github.meistersky.telegram.service;

import com.github.meistersky.telegram.repository.entity.UserGroup;

import java.util.List;
import java.util.Optional;

/**
 * Service for manipulating with {@link UserGroup}.
 */
public interface UserGroupService {

    UserGroup save(Long chatId, String title, String users);

    void save(UserGroup userGroup);

    void delete(UserGroup userGroup);

    boolean isExist(Long chatId, String title);

    List<UserGroup> findAllByChatId(Long chatId);

    UserGroup findByTitle(Long chatId, String title);

    Optional<UserGroup> findById(Integer id);
}
