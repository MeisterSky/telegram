package com.github.meistersky.telegram.service;

import com.github.meistersky.telegram.repository.UserGroupRepository;
import com.github.meistersky.telegram.repository.entity.UserGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserGroupServiceImpl implements UserGroupService {

    private final UserGroupRepository userGroupRepository;

    @Autowired
    public UserGroupServiceImpl(UserGroupRepository userGroupRepository) {
        this.userGroupRepository = userGroupRepository;
    }

    @Override
    public UserGroup save(Long chatId, String title, String users) {
        return null;
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
