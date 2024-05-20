package com.tms.configuaration.entity;


import com.tms.common.generics.AbstractDomainBasedEntity;
import com.tms.common.generics.IDto;
import com.tms.common.models.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.Objects;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "approval_users")
public class ApprovalUser extends AbstractDomainBasedEntity implements IDto {



    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "user_id", nullable = false,insertable = false,updatable = false)
    private Long userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "approval_setting_id")
    private ApprovalSetting approvalSetting;

    @Column(name = "approval_setting_id", insertable = false, updatable = false)
    private Long approvalSettingId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ApprovalUser that)) return false;
        if (!super.equals(o)) return false;
        return Objects.equals(getUser(), that.getUser());
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), getUser());
    }
}
