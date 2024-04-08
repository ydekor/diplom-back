package com.ydekor.diplomback.web.controller;

import com.ydekor.diplomback.exception.BaseException;
import com.ydekor.diplomback.exception.SimpleException;
import com.ydekor.diplomback.web.dto.ResponseError;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
public abstract class ExceptionHandlerController {

    @ExceptionHandler()
    public ResponseEntity<Object> exceptionHandler(BaseException ex) {
        log.error(ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(new ResponseError(ex));
    }

    @ExceptionHandler()
    public ResponseEntity<Object> exceptionHandler(MethodArgumentTypeMismatchException ex) {
        log.error(ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(new ResponseError(new SimpleException(ex.getMessage())));
    }

    @ExceptionHandler()
    public ResponseEntity<Object> exceptionHandler(DataIntegrityViolationException ex) {
        log.error(ex.getMessage());

        return ResponseEntity
                .badRequest()
                .body(new ResponseError(new SimpleException(ex.getMessage())));
    }

}
