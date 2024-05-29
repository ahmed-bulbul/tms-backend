package com.tms.common.payload.response;

import com.tms.projects.entity.Project;
import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserResponseDto {
    private Long id;
    private String username;
    private String email;



}
