package com.developer.test.global;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public String handleException(Exception ex, HttpServletRequest request, Model model) {
        logDetailedError(request, HttpStatus.INTERNAL_SERVER_ERROR, ex);
        model.addAttribute("error", "Internal Server Error");
        return "error/500";
    }

    private void logDetailedError(HttpServletRequest request, HttpStatus status, Exception ex) {
        String method = request.getMethod();
        String path = request.getRequestURI();
        logger.error("HTTP Method: {}, Request Path: {}, Response Status: {}, Error: {}",
                method, path, status.value(), ex.getMessage());
    }
}


