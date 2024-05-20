package com.tms.common.controllers;

import com.tms.common.payload.request.FeatureRolePayload;
import com.tms.common.payload.response.FeatureRoleViewModel;
import com.tms.common.service.FeatureRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feature-role")
public class FeatureRoleController {
    private final FeatureRoleService featureRoleService;

    @Autowired
    public FeatureRoleController(FeatureRoleService featureRoleService) {
        this.featureRoleService = featureRoleService;
    }

    @GetMapping("/{id}")
    public FeatureRoleViewModel roleFeatures(@PathVariable("id") Long roleId) {
        return featureRoleService.featuresByRoleId(roleId);
    }

    @PostMapping
    public int assign(@RequestBody FeatureRolePayload payload) {
        return featureRoleService.assign(payload);
    }
}
