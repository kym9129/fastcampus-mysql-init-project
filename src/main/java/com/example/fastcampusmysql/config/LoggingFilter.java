package com.example.fastcampusmysql.config;

import lombok.extern.slf4j.Slf4j;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class LoggingFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest servletRequest = (HttpServletRequest) request;
        String method = servletRequest.getMethod();
        String uri = servletRequest.getRequestURI();
        String queryString = servletRequest.getQueryString();

        log.info("### 요청 정보 ###");
        log.info("uri: [{}] {}", method, uri);
        log.info("queryString: {}", queryString);

        chain.doFilter(request, response);
    }
}
