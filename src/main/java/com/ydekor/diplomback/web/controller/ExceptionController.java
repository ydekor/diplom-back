package com.ydekor.diplomback.web.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@ResponseBody
public class ExceptionController extends ResponseEntityExceptionHandler {
    public record ErrorMessageResponse (String message) {}

    @ExceptionHandler(value = {RuntimeException.class})
    ResponseEntity<ErrorMessageResponse> exceptionHandler(RuntimeException ex) {
        return new ResponseEntity<>(new ErrorMessageResponse(ex.getMessage()), HttpStatus.BAD_REQUEST);
    }
}
