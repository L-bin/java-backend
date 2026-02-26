package com.developer.test.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.ContentCachingRequestWrapper;
import org.springframework.web.util.ContentCachingResponseWrapper;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@Component
public class LogFilter extends OncePerRequestFilter {

    private static final Logger logger = LoggerFactory.getLogger(LogFilter.class);

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        ContentCachingRequestWrapper requestWrapper = new ContentCachingRequestWrapper(request);
        ContentCachingResponseWrapper responseWrapper = new ContentCachingResponseWrapper(response);

        long startTime = System.currentTimeMillis();

        try {
            filterChain.doFilter(requestWrapper, responseWrapper);
        } finally {
            long duration = System.currentTimeMillis() - startTime;
            logRequestAndResponse(requestWrapper, responseWrapper, duration);

            responseWrapper.copyBodyToResponse();
        }
    }

    private void logRequestAndResponse(ContentCachingRequestWrapper request, ContentCachingResponseWrapper response, long duration) {
        String method = request.getMethod();
        String path = request.getRequestURI();
        if (request.getQueryString() != null) {
            path += "?" + request.getQueryString();
        }
        String requestBody = new String(request.getContentAsByteArray(), StandardCharsets.UTF_8);
        String responseBody = new String(response.getContentAsByteArray(), StandardCharsets.UTF_8);

        StringBuilder logMessage = new StringBuilder("HTTP Method: {}, Path: {}, Status: {}, Duration: {} ms, Request Body: {}, Response Body: {}");

        int status = response.getStatus();
        if (status >= 400 && status < 500) {
            logger.warn(logMessage.toString(), method, path, status, duration, requestBody, responseBody);
        } else if (status >= 500) {
            logger.error(logMessage.toString(), method, path, status, duration, requestBody, responseBody);
        } else {
            logger.info(logMessage.toString(), method, path, status, duration, requestBody, responseBody);
        }
    }
}
