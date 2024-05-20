package com.tms.common.repository;

import com.tms.common.models.FeatureRole;
import com.tms.common.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FeatureRoleRepository extends JpaRepository<FeatureRole, Long> {
    List<FeatureRole> findAllByRole(Role role);
}
