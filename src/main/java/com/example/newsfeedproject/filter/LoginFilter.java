package com.example.newsfeedproject.filter;

import lombok.extern.slf4j.Slf4j;
import jakarta.servlet.*;

import java.io.IOException;

@Slf4j
public class LoginFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
    }
}
