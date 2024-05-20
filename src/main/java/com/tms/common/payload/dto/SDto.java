package com.tms.common.payload.dto;

public interface SDto {
    default Boolean getIsActive() {
        return true;
    }
    default Boolean getIsDesc(){
        return false;
    }
}
