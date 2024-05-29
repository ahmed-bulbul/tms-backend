package com.tms.projects.entity;

import com.fasterxml.jackson.annotation.JsonValue;
import com.tms.common.constant.ApplicationConstant;
import com.tms.common.constant.ErrorId;
import com.tms.common.exception.AppServerException;
import com.tms.common.models.OrganizationTypeEnum;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

import java.util.HashMap;
import java.util.Map;

public enum CategoryEnum {
    WEB_APPLICATION(101L),
    MOBILE_APPLICATION(102L),
    DESKTOP_APPLICATION(103L);

    private static final Map<Long, CategoryEnum> CategoryMap = new HashMap<>();

    static {
        for (CategoryEnum cm : CategoryEnum.values()) {
            CategoryMap.put(cm.getCategoryTypeId(), cm);
        }
    }

    private final Long categoryTypeId;


    CategoryEnum(Long categoryTypeId) {
        this.categoryTypeId = categoryTypeId;
    }

    public static CategoryEnum byId(Long id) {
        if (!CategoryMap.containsKey(id)) {
            throw new AppServerException(
                    ErrorId.DATA_NOT_FOUND,
                    HttpStatus.BAD_REQUEST,
                    MDC.get(ApplicationConstant.TRACE_ID));
        }
        return CategoryMap.get(id);
    }

    @JsonValue
    public Long getCategoryTypeId() {
        return this.categoryTypeId;
    }
}
