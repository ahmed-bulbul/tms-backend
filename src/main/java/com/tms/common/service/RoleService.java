package com.tms.common.service;

import com.tms.common.constant.ApplicationConstant;
import com.tms.common.constant.ERole;
import com.tms.common.constant.ErrorId;
import com.tms.common.exception.AppServerException;
import com.tms.common.repository.RoleRepository;
import com.tms.common.models.AccessRight;
import com.tms.common.models.Role;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

@Service
public class RoleService {

    private final RoleRepository roleRepository;
    private static final Logger LOGGER = LoggerFactory.getLogger(RoleService.class);

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    public Role findById(Integer id) {
        if (Objects.isNull(id)) {
            throw new AppServerException(
                    ErrorId.ID_IS_REQUIRED,
                    HttpStatus.BAD_REQUEST,
                    MDC.get(ApplicationConstant.TRACE_ID));
        }
        return roleRepository.findByIdAndIsDeletedFalse(id).orElseThrow(() -> {
            throw new AppServerException(
                    ErrorId.ROLE_NOT_EXISTS,
                    HttpStatus.NOT_FOUND,
                    MDC.get(ApplicationConstant.TRACE_ID));
        });
    }

    public Role findByName(ERole role){
        if (Objects.isNull(role)) {
            throw new AppServerException(
                    ErrorId.ID_IS_REQUIRED,
                    HttpStatus.BAD_REQUEST,
                    MDC.get(ApplicationConstant.TRACE_ID));
        }
        return roleRepository.findByName(role).orElseThrow(() -> {
            throw new AppServerException(
                    ErrorId.ROLE_NOT_EXISTS,
                    HttpStatus.NOT_FOUND,
                    MDC.get(ApplicationConstant.TRACE_ID));
        });
    }

    public Map<String, Integer> getRoleAccessPermission(Integer roleId) {
        Role role = this.findById(roleId);
        Set<AccessRight> accessRightSet = role.getAccessRightSet();
        Map<String, Integer> roleAccessMap = new HashMap<>();

        for (AccessRight accessRight : accessRightSet) {
            try {
                String key = StringUtils.upperCase(
                        accessRight.getConfigSubmoduleItem().getSubModule().getModule().getModuleName()
                                .replaceAll(ApplicationConstant.SPACE_REGEX, ApplicationConstant.UNDERSCORE))
                        + ApplicationConstant.UNDERSCORE
                        + StringUtils.upperCase(accessRight.getConfigSubmoduleItem().getSubModule().getSubmoduleName()
                        .replaceAll(ApplicationConstant.SPACE_REGEX, ApplicationConstant.UNDERSCORE))
                        + ApplicationConstant.UNDERSCORE
                        + StringUtils.upperCase(accessRight.getConfigSubmoduleItem().getItemName()
                        .replaceAll(ApplicationConstant.SPACE_REGEX, ApplicationConstant.UNDERSCORE))
                        + ApplicationConstant.UNDERSCORE
                        + StringUtils.upperCase(accessRight.getAction().getActionName()
                        .replaceAll(ApplicationConstant.SPACE_REGEX, ApplicationConstant.UNDERSCORE));
                roleAccessMap.put(key, accessRight.getId());
            } catch (Exception ex) {
                LOGGER.error("Can't prepare key of access right. Exception: {}", ex.getMessage());
            }
        }

        return roleAccessMap;
    }
}
