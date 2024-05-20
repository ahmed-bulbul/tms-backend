package com.tms.common.service.impl;


import com.tms.common.constant.ApplicationConstant;
import com.tms.common.constant.FeatureType;
import com.tms.common.repository.FeatureRoleRepository;
import com.tms.common.service.FeatureRoleService;
import com.tms.common.models.FeatureRole;
import com.tms.common.models.Role;
import com.tms.common.payload.request.FeatureRolePayload;
import com.tms.common.payload.response.FeatureRoleViewModel;
import com.tms.common.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
public class FeatureRoleServiceImpl implements FeatureRoleService {

    private final RoleService roleService;
    private final FeatureRoleRepository repository;
    private final List<FeatureRole> featureRoles;

    public FeatureRoleServiceImpl(RoleService roleService, FeatureRoleRepository repository, List<FeatureRole> featureRoles) {
        this.roleService = roleService;
        this.repository = repository;
        this.featureRoles = featureRoles;
    }

    @Override
    public int assign(FeatureRolePayload payload) {
        Role role = getRole(payload.getRoleId());
        prepareEntities(payload, role);
        List<FeatureRole> featureRoleList = repository.findAllByRole(role);
        repository.deleteAllInBatch(featureRoleList);
        repository.saveAll(featureRoles);
        return ApplicationConstant.DEFAULT_SUCCESS_RESPONSE;
    }

    private Role getRole(Integer roleId) {
        return roleService.findById(roleId);
    }

    private void prepareEntities(FeatureRolePayload payload, Role role) {
        prepareTypeWiseEntities(payload.getModuleIds(), FeatureType.MODULE, role);
        prepareTypeWiseEntities(payload.getSubModuleIds(), FeatureType.SUB_MODULE, role);
        prepareTypeWiseEntities(payload.getSubModuleItemIds(), FeatureType.SUB_MODULE_ITEM, role);
    }

    private void prepareTypeWiseEntities(Set<Long> featureIDs, FeatureType featureType, Role role) {
        for (Long featureID : featureIDs) {
            FeatureRole featureRole = new FeatureRole();
            featureRole.setRole(role);
            featureRole.setFeatureId(featureID);
            featureRole.setFeatureType(featureType);
            featureRoles.add(featureRole);
        }
    }

    @Override
    public FeatureRoleViewModel featuresByRoleId(Long id) {
        Role role = getRole(id.intValue());
        List<FeatureRole> allByRole = repository.findAllByRole(role);
        return convertToViewModel(allByRole);
    }

    private FeatureRoleViewModel convertToViewModel(List<FeatureRole> allByRole) {
        FeatureRoleViewModel viewModel = new FeatureRoleViewModel();
        viewModel.setModuleIds(getFeatureIdsByType(allByRole, FeatureType.MODULE));
        viewModel.setSubModuleIds(getFeatureIdsByType(allByRole, FeatureType.SUB_MODULE));
        viewModel.setSubModuleItemIds(getFeatureIdsByType(allByRole, FeatureType.SUB_MODULE_ITEM));
        return viewModel;
    }

    private Set<Long> getFeatureIdsByType(List<FeatureRole> features, FeatureType featureType) {
        return features.stream()
                .filter(matches(featureType))
                .map(FeatureRole::getFeatureId)
                .collect(Collectors.toSet());
    }

    private Predicate<FeatureRole> matches(FeatureType featureType) {
        return featureRole -> featureRole.getFeatureType().equals(featureType);
    }
}
