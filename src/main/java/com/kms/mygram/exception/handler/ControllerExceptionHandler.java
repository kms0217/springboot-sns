package com.kms.mygram.exception.handler;

import com.kms.mygram.dto.ResponseDto;
import com.kms.mygram.exception.ApiException;
import com.kms.mygram.exception.ProfileEditValidException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class ControllerExceptionHandler {

    @ExceptionHandler(ProfileEditValidException.class)
    public ResponseEntity<?> ProfileValidException(ProfileEditValidException e){
        return new ResponseEntity<>(new ResponseDto<>(e.getMessage(), e.getErrors()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ApiException.class)
    public ResponseEntity<?> ApiExceptionHandle(ApiException e){
        return new ResponseEntity<>(new ResponseDto<>(e.getMessage(), null), HttpStatus.BAD_REQUEST);
    }
}
