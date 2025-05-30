package com.example.portfolio.exception;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

@ControllerAdvice
public class GlobalExceptionHandler {

    // 404 Not Found の共通処理
    @ExceptionHandler(NoHandlerFoundException.class)
    public String handleNotFound(NoHandlerFoundException ex, Model model, HttpServletRequest request) {
        model.addAttribute("errorMessage", "ページが見つかりません。");
        model.addAttribute("statusCode", 404);
        model.addAttribute("path", request.getRequestURI());
        return "error/404";
    }

    // その他の例外の共通処理（例：500）
    @ExceptionHandler(Exception.class)
    public String handleException(Exception ex, Model model, HttpServletRequest request) {
        model.addAttribute("errorMessage", "予期せぬエラーが発生しました。");
        model.addAttribute("statusCode", 500);
        model.addAttribute("path", request.getRequestURI());
        return "error/500";
    }
    
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDenied(AccessDeniedException ex, Model model, HttpServletRequest request) {
        model.addAttribute("errorMessage", "アクセスが拒否されました。権限が必要です。");
        model.addAttribute("statusCode", 403);
        model.addAttribute("path", request.getRequestURI());
        return "error/403";
    }
}
