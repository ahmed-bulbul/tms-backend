package com.tms.common.exception;


import com.tms.common.constant.ApplicationConstant;
import com.tms.common.constant.ErrorId;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;
import static org.apache.commons.lang3.StringUtils.*;

import java.util.Objects;
import java.util.Set;

import static io.jsonwebtoken.lang.Strings.capitalize;
import static org.apache.logging.log4j.util.Chars.SPACE;

@ControllerAdvice
public class RestExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
        ApiError apiError = new ApiError();
        AppError engineeringManagementError =
                new AppError(ErrorId.SYSTEM_ERROR, ex.getLocalizedMessage());
        apiError.addError(engineeringManagementError);
        ex.printStackTrace();
        return new ResponseEntity(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public final ResponseEntity<Object>
    handleConstraintViolationExceptionAllException(ConstraintViolationException ex, WebRequest request) {
        ApiError apiError = new ApiError();
        Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
        violations.forEach(violation -> {
            AppError reservationError = getEngineeringManagementError(violation.getMessageTemplate());
            apiError.addError(reservationError);
        });
        return new ResponseEntity(apiError, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AppServerException.class)
    public final ResponseEntity<Object> handleEngineeringManagementServerException(
            AppServerException ex, WebRequest request) {
        ApiError apiError = new ApiError();
        AppError reservationError = getEngineeringManagementError(ex.getErrorId());
        apiError.addError(reservationError);
        return new ResponseEntity(apiError, ex.getStatus());
    }



    private AppError getEngineeringManagementError(String code) {
        AppError engineeringManagementError = ErrorCodeReader.getEngineeringManagementError(code);
        if (Objects.isNull(engineeringManagementError)) {
            return ErrorCodeReader.getErrorByMessage(code);
        }
        return engineeringManagementError;
    }

    private AppError getEngineeringManagementError(String code, String message) {
        AppError engineeringManagementError = ErrorCodeReader.getEngineeringManagementError(code);
        if (Objects.isNull(engineeringManagementError)) {
            return ErrorCodeReader.getErrorByMessage(message);
        }
        return engineeringManagementError;
    }

    private String buildErrorMessage(FieldError error) {
        return capitalize(StringUtils.join(splitByCharacterTypeCamelCase(emptyFieldErrorIfNull(error)
        ), SPACE)) + SPACE + error.getDefaultMessage();
    }

    private String emptyFieldErrorIfNull(FieldError fieldError) {
        return Objects.isNull(fieldError) ? ApplicationConstant.EMPTY_STRING : fieldError.getField();
    }
}
