package com.tms.common.exception;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AppError {
    private String code;
    private String message;

    public AppError(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
