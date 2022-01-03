package com.kms.mygram.common.validator;

import com.kms.mygram.user.dto.UserRequestDto;
import org.junit.jupiter.api.*;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

class EmailPhoneNumberValidatorTest {

    private static ValidatorFactory validatorFactory;
    private static Validator validator;
    private static UserRequestDto userRequestDto;

    @BeforeAll
    public static void init(){
        validatorFactory = Validation.buildDefaultValidatorFactory();
        validator = validatorFactory.getValidator();
        userRequestDto = new UserRequestDto();
        userRequestDto.setName("testName");
        userRequestDto.setPassword("testPassword");
        userRequestDto.setUsername("testUserName");
    }

    @AfterAll
    public static void close(){
        validatorFactory.close();
    }

    @DisplayName("1. 이메일 성공 Case")
    @Test
    void test_1(){
        Set<ConstraintViolation<UserRequestDto>> violations;
        userRequestDto.setEmailOrPhoneNumber("test@naver.com");
        violations = validator.validate(userRequestDto);
        Assertions.assertTrue(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("test@ssss.co.kr");
        violations = validator.validate(userRequestDto);
        Assertions.assertTrue(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("test@student.42.kr");
        violations = validator.validate(userRequestDto);
        Assertions.assertTrue(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("t@student.42.kr");
        violations = validator.validate(userRequestDto);
        Assertions.assertTrue(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("213123t@student.42.kr");
        violations = validator.validate(userRequestDto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @DisplayName("2. 이메일 실패 Case")
    @Test
    void test_2(){
        Set<ConstraintViolation<UserRequestDto>> violations;
        userRequestDto.setEmailOrPhoneNumber("test@n@aver.com");
        violations = validator.validate(userRequestDto);
        Assertions.assertFalse(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("test@ssss.co.kr.");
        violations = validator.validate(userRequestDto);
        Assertions.assertFalse(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("teststudent.42.kr");
        violations = validator.validate(userRequestDto);
        Assertions.assertFalse(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("@t@student.42.kr");
        violations = validator.validate(userRequestDto);
        Assertions.assertFalse(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("213123t@.student.42.kr");
        violations = validator.validate(userRequestDto);
        Assertions.assertFalse(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("213123t.@student.42.kr");
        violations = validator.validate(userRequestDto);
        Assertions.assertFalse(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("213123t@stud@ent.42.kr");
        violations = validator.validate(userRequestDto);
        Assertions.assertFalse(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("213123t");
        violations = validator.validate(userRequestDto);
        Assertions.assertFalse(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("2sssss@naver.c");
        violations = validator.validate(userRequestDto);
        Assertions.assertFalse(violations.isEmpty());
    }

    @DisplayName("3. 핸드폰 성공 Case")
    @Test
    void test_3(){
        Set<ConstraintViolation<UserRequestDto>> violations;
        userRequestDto.setEmailOrPhoneNumber("01012341234");
        violations = validator.validate(userRequestDto);
        Assertions.assertTrue(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("010-1234-1234");
        violations = validator.validate(userRequestDto);
        Assertions.assertTrue(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("011-123-1234");
        violations = validator.validate(userRequestDto);
        Assertions.assertTrue(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("010.1234.1234");
        violations = validator.validate(userRequestDto);
        Assertions.assertTrue(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("010-1234.1234");
        violations = validator.validate(userRequestDto);
        Assertions.assertTrue(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("010.1234-1234");
        violations = validator.validate(userRequestDto);
        Assertions.assertTrue(violations.isEmpty());
    }

    @DisplayName("4. 핸드폰 실패 Case")
    @Test
    void test_4(){
        Set<ConstraintViolation<UserRequestDto>> violations;
        userRequestDto.setEmailOrPhoneNumber("123456");
        violations = validator.validate(userRequestDto);
        Assertions.assertFalse(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("013-4444-4444");
        violations = validator.validate(userRequestDto);
        Assertions.assertFalse(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("010-x000-2222");
        violations = validator.validate(userRequestDto);
        Assertions.assertFalse(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("010-22222-2222");
        violations = validator.validate(userRequestDto);
        Assertions.assertFalse(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("01.0.3333.3333");
        violations = validator.validate(userRequestDto);
        Assertions.assertFalse(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("101.3233.2222");
        violations = validator.validate(userRequestDto);
        Assertions.assertFalse(violations.isEmpty());
        userRequestDto.setEmailOrPhoneNumber("010-4444-444");
        violations = validator.validate(userRequestDto);
        Assertions.assertFalse(violations.isEmpty());
    }
}