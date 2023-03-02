package com.example.pytest.base;

import com.example.pytest.StatusResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionController {

    private final StatusResponse FAILED_RESULT = StatusResponse.FAILED();

    @ExceptionHandler(MethodArgumentNotValidException.class) // 資料參數驗證不過
    @ResponseBody
    public ResponseEntity<Object> exception(MethodArgumentNotValidException e, HttpServletRequest request) {

        log.error("請求[{}] {} 的參數校驗發生錯誤", request.getMethod(), request.getRequestURL());
        for (ObjectError objectError: e.getBindingResult().getAllErrors()) {
            FieldError fieldError = (FieldError) objectError;
            log.error("參數 {} = {} 校驗錯誤: {}", fieldError.getField(), fieldError.getRejectedValue(), fieldError.getDefaultMessage());
        }
        return ResponseEntity.badRequest().body(FAILED_RESULT);
    }

    @ExceptionHandler(NoSuchElementException.class) // 無法從資料庫找到使用者
    @ResponseBody
    public ResponseEntity<Object> exception(NoSuchElementException e, HttpServletRequest request) {
        log.error("請求[{}] {} 無法從資料庫找到使用者: {}", request.getMethod(), request.getRequestURL(), e.getMessage());
        return  ResponseEntity.badRequest().body(FAILED_RESULT);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    @ResponseBody
    public ResponseEntity<Object> exception(DataIntegrityViolationException e, HttpServletRequest request) {
        log.error("請求[{}] {} 的參數校驗發生錯誤: {}", request.getMethod(), request.getRequestURL(), e.getMessage());
        return ResponseEntity.badRequest().body(FAILED_RESULT);
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<Object> exception(RuntimeException e, HttpServletRequest request) {
        log.error("請求[{}] {} 發生異常: {}", request.getMethod(), request.getRequestURL(), e.getMessage());
        return ResponseEntity.badRequest().body(FAILED_RESULT);
    }
}
