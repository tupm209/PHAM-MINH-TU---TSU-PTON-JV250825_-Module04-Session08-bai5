package com.example.md04_ss08_bai5.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<String> handlerSizeExceeded(MaxUploadSizeExceededException ex){
        return new ResponseEntity<>("File quá lớn (Tối đa 1MB)", HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handlerException(Exception ex){
        return new ResponseEntity<>("Lỗi hệ thống", HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
