package com.example.newsfeedproject.filter;

import com.example.newsfeedproject.dto.login.LoginResponseDto;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.PatternMatchUtils;

import java.io.IOException;

import static com.example.newsfeedproject.session.Const.LOGIN_USER;

@Slf4j
public class LoginFilter implements Filter {
    private static final String[] WHITE_LIST = {"/","/users/signup","/login","/logout"};

    @Override
    public void doFilter(
            ServletRequest servletRequest,
            ServletResponse servletResponse,
            FilterChain filterChain
    ) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) servletRequest;
        String requestURL = httpServletRequest.getRequestURI();

        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;

        if (!isWhiteList(requestURL)) {
            HttpSession session = httpServletRequest.getSession(false);

            if (session == null || session.getAttribute("LOGIN_USER") == null) {
                log.warn("Unauthorized access attempt to {}", requestURL);
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpServletResponse.getWriter().write("로그인 해주세요");
                return;
            }

            // 세션에 저장된 객체가 LoginResponseDto인지 확인
            LoginResponseDto loginUser = (LoginResponseDto) session.getAttribute("LOGIN_USER");
            if (loginUser == null) {
                log.warn("Invalid session data for {}", requestURL);
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpServletResponse.getWriter().write("로그인 해주세요");
                return;
            }
        }
        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isWhiteList(String requestURL) {
        return PatternMatchUtils.simpleMatch(WHITE_LIST, requestURL);
    }
}
