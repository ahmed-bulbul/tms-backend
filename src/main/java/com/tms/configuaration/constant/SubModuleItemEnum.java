package com.tms.configuaration.constant;


import com.tms.common.constant.ApplicationConstant;
import com.tms.common.constant.ErrorId;
import com.tms.common.exception.AppServerException;
import com.fasterxml.jackson.annotation.JsonValue;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public enum SubModuleItemEnum {

    STORE_DEMAND(50000L, SubModuleEnum.PARTS_DEMAND),
    PENDING_DEMAND(50001L, SubModuleEnum.PARTS_DEMAND),
    APPROVED_DEMAND(50002L, SubModuleEnum.PARTS_DEMAND),
    DEMAND_REPORT(50003L, SubModuleEnum.PARTS_DEMAND),


    ISSUE_DEMAND(50004L, SubModuleEnum.PARTS_ISSUE),
    PENDING_ISSUE(50005L, SubModuleEnum.PARTS_ISSUE),
    APPROVED_ISSUE(50006L, SubModuleEnum.PARTS_ISSUE),
    ISSUE_REPORT(50007L, SubModuleEnum.PARTS_ISSUE),

    AIRCRAFT(50008L, SubModuleEnum.AIRCRAFT),
    SEATING_CONFIGURATION(50009L, SubModuleEnum.AIRCRAFT),

    USER(50010L,SubModuleEnum.USER_MANAGEMENT),
    ROLE(50011L,SubModuleEnum.USER_MANAGEMENT),
    LOCATION(50012L,SubModuleEnum.USER_MANAGEMENT);



    private static final Map<Long, SubModuleItemEnum> ConfigSubModuleItemMap = new HashMap<>();

    static {
        for (SubModuleItemEnum csmi : SubModuleItemEnum.values()) {
            ConfigSubModuleItemMap.put(csmi.getSubModuleItemId(), csmi);
        }
    }

    private final Long subModuleItemId;
    private final SubModuleEnum subModuleEnum;

    SubModuleItemEnum(Long subModuleItemId, SubModuleEnum subModuleEnum) {
        this.subModuleEnum = subModuleEnum;
        this.subModuleItemId = subModuleItemId;

    }

    public static SubModuleItemEnum byId(Integer id) {
        if (!ConfigSubModuleItemMap.containsKey(id)) {
            throw new AppServerException(
                    ErrorId.DATA_NOT_FOUND,
                    HttpStatus.BAD_REQUEST,
                    MDC.get(ApplicationConstant.TRACE_ID));
        }
        return ConfigSubModuleItemMap.get(id);
    }

    @JsonValue
    public Long getSubModuleItemId() {
        return this.subModuleItemId;
    }

    @JsonValue
    public SubModuleEnum getSubModule() {
        return this.subModuleEnum;
    }
}