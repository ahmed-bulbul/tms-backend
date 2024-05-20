package com.tms.common.service;

import com.tms.common.payload.request.FeatureRolePayload;
import com.tms.common.payload.response.FeatureRoleViewModel;

public interface FeatureRoleService {
    int assign(FeatureRolePayload payload);

    FeatureRoleViewModel featuresByRoleId(Long id);
}
