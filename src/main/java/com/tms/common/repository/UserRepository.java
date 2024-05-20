package com.tms.common.repository;

import java.util.Optional;
import java.util.Set;

import com.tms.common.payload.projection.UsernameProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.common.models.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
  Optional<User> findByUsername(String username);

  Boolean existsByUsername(String username);

  Boolean existsByEmail(String email);

  Set<UsernameProjection> findUserByIdIn(Set<Long> idList);
}
