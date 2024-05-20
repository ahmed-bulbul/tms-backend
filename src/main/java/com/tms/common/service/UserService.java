package com.tms.common.service;

import com.tms.common.models.User;
import com.tms.common.payload.projection.UsernameProjection;

import java.util.Optional;
import java.util.Set;

public interface UserService {
    Optional<User> findByUsername(String username);

    Set<UsernameProjection> findUsernameByIdList(Set<Long> idList);
}
