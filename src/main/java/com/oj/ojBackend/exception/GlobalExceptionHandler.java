package com.oj.ojBackend.exception;

import com.oj.ojBackend.common.BaseResponse;
import com.oj.ojBackend.common.ErrorCode;
import com.oj.ojBackend.common.ResultUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * global exception handler
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * custom exception
     * @param e
     * @return
     */
    @ExceptionHandler(BusinessException.class)
    public BaseResponse<?> businessExceptionHandler(BusinessException e) {
        log.error("BusinessException", e);
        return ResultUtils.error(e.getCode(), e.getMessage());
    }

    /**
     * general exception
     * @param e
     * @return
     */
    @ExceptionHandler(RuntimeException.class)
    public BaseResponse<?> runtimeExceptionHandler(RuntimeException e) {
        log.error("RuntimeException", e);
        return ResultUtils.error(ErrorCode.SYSTEM_ERROR, "system error occur");
    }
}
