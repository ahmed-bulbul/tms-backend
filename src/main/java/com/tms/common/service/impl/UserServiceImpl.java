package com.tms.common.service.impl;

import com.tms.common.repository.UserRepository;
import com.tms.common.models.User;
import com.tms.common.payload.projection.UsernameProjection;
import com.tms.common.service.UserService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public Set<UsernameProjection> findUsernameByIdList(Set<Long> idList) {
        return userRepository.findUserByIdIn(idList);
    }

}
