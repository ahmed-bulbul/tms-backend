package com.tms.common.repository;

import com.tms.common.models.AccessRight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Set;


@Repository
public interface AccessRightRepository extends JpaRepository<AccessRight, Integer> {
    Set<AccessRight> findAllByIdIn(Set<Integer> accessRightIds);
}
