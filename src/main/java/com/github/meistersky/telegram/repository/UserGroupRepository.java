package com.github.meistersky.telegram.repository;

import com.github.meistersky.telegram.repository.entity.TelegramUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.github.meistersky.telegram.repository.entity.UserGroup;

import java.util.List;

/**
 * {@link Repository} for {@link UserGroup} entity.
 */
@Repository
public interface UserGroupRepository extends JpaRepository<UserGroup, Long> {
}
