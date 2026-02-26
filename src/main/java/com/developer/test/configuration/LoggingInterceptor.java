package com.developer.test.configuration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoggingInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(LoggingInterceptor.class);

    private long startTime;

    @Override
    public boolean preHandle(HttpServletRequest request, javax.servlet.http.HttpServletResponse response, Object handler) throws Exception {
        startTime = System.currentTimeMillis();
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;

        String method = request.getMethod();
        String path = request.getRequestURI();
        int status = response.getStatus();

        if (status >= 400) {
            // 记录详细错误日志
            logger.error("HTTP Method: {}, Path: {}, Status: {}, Duration: {} ms, Error: {}",
                    method, path, status, duration, getErrorMessage(ex));
        } else {
            // 记录正常响应日志
            logger.info("HTTP Method: {}, Path: {}, Status: {}, Duration: {} ms", method, path, status, duration);
        }
    }

    private String getErrorMessage(Exception ex) {
        if (ex != null) {
            return ex.getMessage();
        }
        return "No additional error message";
    }
}
