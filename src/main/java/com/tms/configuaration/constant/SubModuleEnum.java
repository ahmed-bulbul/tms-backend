package com.tms.configuaration.constant;

import com.tms.common.constant.ApplicationConstant;
import com.tms.common.constant.ErrorId;
import com.tms.common.exception.AppServerException;
import com.fasterxml.jackson.annotation.JsonValue;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public enum SubModuleEnum {
    PARTS_DEMAND(4000L, ModuleEnum.STORE),
    PARTS_ISSUE(4001L, ModuleEnum.STORE),
    AIRCRAFT(4002L, ModuleEnum.PLANNING),
    USER_MANAGEMENT(4003L,ModuleEnum.COMMON);


    private static final Map<Long, SubModuleEnum> ConfigSubModuleMap = new HashMap<>();

    static {
        for (SubModuleEnum csm : SubModuleEnum.values()) {
            ConfigSubModuleMap.put(csm.getSubModuleId(), csm);
        }
    }

    private final Long subModuleId;
    private final ModuleEnum moduleEnum;

    SubModuleEnum(Long subModuleId, ModuleEnum moduleEnum) {
        this.subModuleId = subModuleId;
        this.moduleEnum = moduleEnum;
    }

    public static SubModuleEnum byId(Integer id) {
        if (!ConfigSubModuleMap.containsKey(id)) {
            throw new AppServerException(
                    ErrorId.DATA_NOT_FOUND,
                    HttpStatus.BAD_REQUEST,
                    MDC.get(ApplicationConstant.TRACE_ID));
        }
        return ConfigSubModuleMap.get(id);
    }

    @JsonValue
    public Long getSubModuleId() {
        return this.subModuleId;
    }

    @JsonValue
    public ModuleEnum getModule() {
        return this.moduleEnum;
    }
}
