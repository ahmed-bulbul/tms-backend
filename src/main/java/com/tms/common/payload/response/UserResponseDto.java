package com.tms.common.payload.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;
    private Long organizationId;

}
