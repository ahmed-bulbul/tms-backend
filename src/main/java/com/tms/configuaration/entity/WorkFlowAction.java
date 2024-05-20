package com.tms.configuaration.entity;


import com.tms.common.generics.AbstractDomainBasedEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "workflow_actions")
public class WorkFlowAction  extends AbstractDomainBasedEntity {


    @Column(name = "name", nullable = false, unique = true)
    private String name;
    @Column(name = "order_no", nullable = false, columnDefinition = "INT")
    private Integer orderNumber;
    @Column(name = "is_show", columnDefinition = "boolean default true", nullable = false)
    private Boolean isShow;
    @Column(name = "label", nullable = false)
    private String label;

    @Transient
    private boolean finalItem;

    public static WorkFlowAction withId(Long workflowActionId) {
        WorkFlowAction workFlowAction = new WorkFlowAction();
        workFlowAction.setId(workflowActionId);
        return workFlowAction;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof WorkFlowAction)) return false;
        if (!super.equals(o)) return false;

        WorkFlowAction that = (WorkFlowAction) o;

        return getId() != null ? getId().equals(that.getId()) : that.getId() == null;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getId() != null ? getId().hashCode() : 0);
        return result;
    }
}
