package com.kms.mygram.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.Map;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProfileEditValidException extends RuntimeException{

    private Map<String, String> errors;

    public ProfileEditValidException(String message, Map<String , String> errors){
        super(message);
        this.errors = errors;
    }

    public Map<String, String> getErrors(){
        return errors;
    }
}
