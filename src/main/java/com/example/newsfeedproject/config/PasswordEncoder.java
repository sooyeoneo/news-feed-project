package com.example.newsfeedproject.config;

import at.favre.lib.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Component;

// 비밀번호 암호화 및 검증
@Component
public class PasswordEncoder {

    /**
     * 입력된 원본 비밀번호를 암호화된 문자열로 변환
     * @param rawPassword 원본 비밀번호
     * @return 암호화된 비밀번호 문자열
     */
    public String encode(String rawPassword) {
        return BCrypt.withDefaults().hashToString(BCrypt.MIN_COST, rawPassword.toCharArray());
    }

    /**
     * 입력된 원본 비밀번호와 암호화된 비밀번호가 일치하는지 확인
     * @param rawPassword 원본 비밀번호
     * @param encodedPassword 암호화된 비밀번호
     * @return 일치 여부 (true: 일치, false: 불일치)
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        // BCrypt 검증기를 사용하여 비밀번호를 비교
        BCrypt.Result result = BCrypt.verifyer().verify(rawPassword.toCharArray(), encodedPassword);
        return result.verified;
    }
}