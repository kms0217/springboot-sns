package com.kms.mygram.exception.handler;

import com.kms.mygram.dto.ResponseDto;
import com.kms.mygram.exception.ApiException;
import com.kms.mygram.exception.ApiForbiddenException;
import com.kms.mygram.exception.PageException;
import com.kms.mygram.exception.ValidException;
import com.kms.mygram.utils.ErrorScript;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {
    //TODO 같은 형식의 반환은 통합하기
    @ExceptionHandler(ValidException.class)
    public ResponseEntity<?> ProfileValidException(ValidException e) {
        return new ResponseEntity<>(new ResponseDto<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> ApiExceptionHandle(ApiException e) {
        return new ResponseEntity<>(new ResponseDto<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiForbiddenException.class)
    public ResponseEntity<?> ForbiddenException(ApiForbiddenException e) {
        return new ResponseEntity<>(new ResponseDto<>(e.getMessage(), null), HttpStatus.FORBIDDEN);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<?> FileSizeException(MaxUploadSizeExceededException e) {
        return new ResponseEntity<>(new ResponseDto<>("file의 크기가 너무 큽니다.", null), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(PageException.class)
    public String PageExceptionHandle(PageException e) {
        return ErrorScript.script(e.getMessage());
    }
}
