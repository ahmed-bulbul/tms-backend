package com.tms.common.models;

import com.fasterxml.jackson.annotation.JsonValue;
import com.tms.common.constant.ApplicationConstant;
import com.tms.common.constant.ErrorId;
import com.tms.common.exception.AppServerException;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public enum OrganizationTypeEnum {
    ROOT_ORG(101L),
    SUB_ORG(102L);

    private static final Map<Long, OrganizationTypeEnum> OrgsMap = new HashMap<>();

    static {
        for (OrganizationTypeEnum cm : OrganizationTypeEnum.values()) {
            OrgsMap.put(cm.getOrgId(), cm);
        }
    }

    private final Long organizationId;


    OrganizationTypeEnum(Long organizationId) {
        this.organizationId = organizationId;
    }

    public static OrganizationTypeEnum byId(Long id) {
        if (!OrgsMap.containsKey(id)) {
            throw new AppServerException(
                    ErrorId.DATA_NOT_FOUND,
                    HttpStatus.BAD_REQUEST,
                    MDC.get(ApplicationConstant.TRACE_ID));
        }
        return OrgsMap.get(id);
    }

    @JsonValue
    public Long getOrgId() {
        return this.organizationId;
    }



}
