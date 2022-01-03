package com.kms.mygram.common.aop;

import com.kms.mygram.common.exception.ValidException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Component
@Aspect
public class ApiValidationAdvice {

    @Before("execution(* com.kms.mygram.controller.api.*.*(*, *, ..))")
    public void BeforeApi(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        for (Object arg:args) {
            if (arg instanceof BindingResult)
                checkResultAndThrow((BindingResult) arg);
        }
    }

    private void checkResultAndThrow(BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            StringBuffer buffer = new StringBuffer("Validation Fail\n");
            for (FieldError error : bindingResult.getFieldErrors())
                buffer.append(error.getDefaultMessage() + "\n");
            throw new ValidException(buffer.toString());
        }
    }
}
