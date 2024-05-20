package com.tms.common.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
public class ApiError {
    List<AppError> apiErrors;
    public void addError(AppError error) {
        if(apiErrors == null) {
            apiErrors = new ArrayList<>();
        }
        apiErrors.add(error);
    }
}
