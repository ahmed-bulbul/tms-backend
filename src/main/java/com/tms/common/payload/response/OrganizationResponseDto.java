package com.tms.common.payload.response;

import com.tms.common.models.OrganizationTypeEnum;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrganizationResponseDto {

    private Long id;

    private String reference;

    private String orgCode;

    private String organizationType;

    private Long organizationTypeId;

    private String name;

}
