package com.tms.configuaration.repository;


import com.tms.common.generics.AbstractRepository;
import com.tms.configuaration.entity.WorkFlowAction;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface WorkFlowActionRepository extends AbstractRepository<WorkFlowAction> {

    List<WorkFlowAction> findByIsActiveTrueAndIdInOrIsShowFalse(Set<Long> workFlowActionIds);

    boolean existsByOrderNumberAndIsActiveTrueAndIdNot(Integer orderNumber, Long id);

    boolean existsByNameAndIsActiveTrueAndIdNot(String name, Long id);

    Optional<WorkFlowAction> findTop1ByIsShowFalseOrderByOrderNumberDesc();

    boolean existsByNameAndIsActiveTrue(String name);

    boolean existsByOrderNumberAndIsActiveTrue(Integer orderNumber);

    Optional<WorkFlowAction> findByName(String name);

    List<WorkFlowAction> findByIsActiveTrueAndIdIn(Set<Long> workFlowActionIds);
}
