package com.tms.store.entity;

import com.tms.common.generics.AbstractDomainBasedEntity;
import com.tms.configuaration.entity.WorkFlowAction;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "locations")
public class Location extends AbstractDomainBasedEntity {

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "workflow_action_id", nullable = false)
    private WorkFlowAction workFlowAction;
    @Column(name = "workflow_action_id", insertable = false, updatable = false)
    private Long workFlowActionId;


}
