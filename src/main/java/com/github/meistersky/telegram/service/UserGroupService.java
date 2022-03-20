package com.github.meistersky.telegram.service;

import com.github.meistersky.telegram.repository.entity.UserGroup;

import java.util.List;
import java.util.Optional;

/**
 * Service for manipulating with {@link UserGroup}.
 */
public interface UserGroupService {

    UserGroup save(Long chatId, String title, String users);

    UserGroup save(UserGroup userGroup);

    Optional<UserGroup> findById(Integer id);

    List<UserGroup> findAll();
}
