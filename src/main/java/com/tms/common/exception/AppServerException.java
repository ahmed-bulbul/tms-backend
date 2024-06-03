package com.tms.common.exception;


import com.tms.common.constant.ApplicationConstant;
import lombok.Data;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;

@Data
public class AppServerException extends RuntimeException {
    /**
     * Serial version UID.
     */
    private static final long serialVersionUID = 1436995162658277359L;
    /**
     * Error id.
     */
    private final String errorId;

    /**
     * trace id.
     */
    private final String traceId;

    private HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

    public AppServerException(String errorId, HttpStatus status, String traceId) {
        this.errorId = errorId;
        this.traceId = traceId;
        this.status = status;
    }

    public static AppServerException badRequest(String errorId) {
        return new AppServerException(errorId, HttpStatus.BAD_REQUEST, MDC.get(
                ApplicationConstant.TRACE_ID));
    }

    public static AppServerException notFound(String errorId) {
        return new AppServerException(errorId, HttpStatus.NOT_FOUND, MDC.get(
                ApplicationConstant.TRACE_ID));
    }

    public static AppServerException dataSaveException(String errorId) {
        return new AppServerException(errorId, HttpStatus.INTERNAL_SERVER_ERROR,
            MDC.get(ApplicationConstant.TRACE_ID));
    }

    public static AppServerException internalServerException(String errorId) {
        return new AppServerException(errorId, HttpStatus.INTERNAL_SERVER_ERROR,
                MDC.get(ApplicationConstant.TRACE_ID));
    }

    public static AppServerException methodNotAllowed(String errorId) {
        return new AppServerException(errorId, HttpStatus.UNAUTHORIZED,
            MDC.get(ApplicationConstant.TRACE_ID));
    }

    public static AppServerException notAuthorized(String errorId) {
        return new AppServerException(
                errorId,
                HttpStatus.FORBIDDEN,
                MDC.get(ApplicationConstant.TRACE_ID)
        );
    }


}
