package com.xxq.configure;

import com.xxq.common.BaseResult;
import com.xxq.common.exception.BizFailException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@ControllerAdvice
public class GlobalExceptionHandler {

    @Autowired
    private MessageSource messageSource;

    @ExceptionHandler(value = Exception.class)
    public Object errorHandler(Exception e) {
        return new BaseResult("9999", e.getMessage(), null);
    }

    @ExceptionHandler(value = BizFailException.class)
    public Object errorHandler(BizFailException e) {
        String message = e.getMessage();
        if (StringUtils.isBlank(message)) {
            message = messageSource.getMessage(e.getErrorCode(), null, null);
        }
        return new BaseResult(e.getErrorCode(), message, null);
    }
}