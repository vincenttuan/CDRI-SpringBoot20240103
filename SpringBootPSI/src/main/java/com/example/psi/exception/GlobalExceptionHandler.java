package com.example.psi.exception;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

// 捕捉全局異常 
@ControllerAdvice
public class GlobalExceptionHandler {

    // 捕捉所有類型的 Exception
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model) {
        model.addAttribute("errorMessage", ex.getMessage());
        return "error";
    }

    // 您可以添加更多的異常處理方法來處理不同類型的異常
}

