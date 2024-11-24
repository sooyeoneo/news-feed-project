package com.example.newsfeedproject.validation;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class PasswordValidation {

    // 비밀번호 유효성 검사 패턴
    // - 최소 8자 이상
    // - 대문자 하나 이상 포함
    // - 소문자 하나 이상 포함
    // - 숫자 하나 이상 포함
    // - 특수문자 하나 이상 포함
    private final String PASSWORD_PATTERN =
            "^(?=.*[A-Z])(?=.*[0-9])(?=.*[a-z])(?=.*[!@#$%^&*()-+=]).{8,}$";

    // 정규식 패턴 객체 생성
    private final Pattern pattern = Pattern.compile(PASSWORD_PATTERN);

    /**
     * 비밀번호가 유효한지 확인하는 메서드
     *
     * @param password 입력받은 비밀번호
     * @return 비밀번호가 패턴을 충족하면 true, 그렇지 않으면 false
     */
    public boolean isValidPassword(String password) {
        // 비밀번호가 null인지 확인
        if(password == null){
            return false; // null인 경우 false 반환
        }
        // 비밀번호가 패턴에 매칭되는지 확인
        return pattern.matcher(password).matches();
    }
}
