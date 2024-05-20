package com.tms.configuaration.constant;


import com.tms.common.constant.ApplicationConstant;
import com.tms.common.constant.ErrorId;
import com.tms.common.exception.AppServerException;
import com.fasterxml.jackson.annotation.JsonValue;
import com.tms.common.models.OrganizationTypeEnum;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public enum ModuleEnum {
    STORE(100L),
    PLANNING(300L),
    COMMON(400L);


    private static final Map<Long, ModuleEnum> ConfigModulesMap = new HashMap<>();

    static {
        for (ModuleEnum cm : ModuleEnum.values()) {
            ConfigModulesMap.put(cm.getConfigModuleId(), cm);
        }
    }

    private final Long configModuleId;

    ModuleEnum(Long configModuleId) {
        this.configModuleId = configModuleId;
    }

    public static ModuleEnum byId(Long id) {
        if (!ConfigModulesMap.containsKey(id)) {
            throw new AppServerException(
                    ErrorId.DATA_NOT_FOUND,
                    HttpStatus.BAD_REQUEST,
                    MDC.get(ApplicationConstant.TRACE_ID));
        }
        return ConfigModulesMap.get(id);
    }

    @JsonValue
    public Long getConfigModuleId() {
        return this.configModuleId;
    }

}
