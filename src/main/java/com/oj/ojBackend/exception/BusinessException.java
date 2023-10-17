package com.oj.ojBackend.exception;

import com.oj.ojBackend.common.ErrorCode;

/**
 * customized exception class
 *
 */
public class BusinessException extends RuntimeException {

    /**
     * error code
     */
    private final int code;

    public BusinessException(int code, String message) {
        super(message);
        this.code = code;
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode.getCode();
    }

    public BusinessException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode.getCode();
    }

    public int getCode() {
        return code;
    }
}
