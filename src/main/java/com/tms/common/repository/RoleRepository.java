package com.tms.common.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tms.common.constant.ERole;
import com.tms.common.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(ERole name);

  Optional<Role> findByIdAndIsDeletedFalse(Integer id);
}
