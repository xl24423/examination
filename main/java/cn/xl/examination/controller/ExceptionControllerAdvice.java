package cn.xl.examination.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class ExceptionControllerAdvice {
    @ExceptionHandler
    public String handlerException(Exception e) {
        log.error("发生其他异常", e);
        return e.getMessage();
    }
}
