package com.tms.common.payload.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class FeatureRolePayload {
    private Long id;

    private Integer roleId;

    @NotEmpty
    private Set<Long> moduleIds;

    @NotEmpty
    private Set<Long> subModuleIds;

    @NotEmpty
    private Set<Long> subModuleItemIds;
}
