package com.gym.bstrong.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {

    private int code;
    private String title;
    private String message;
    private Map<String, String> errors;

    public static ErrorResponse generalError(int code, String title, String message) {
        return new ErrorResponse(code, title, message, new HashMap<>());
    }

    public static ErrorResponse notFound(String message) {
        return new ErrorResponse(404, "Not Found", message, new HashMap<>());
    }

    public static ErrorResponse validationError(Map<String, String> errors) {
        return new ErrorResponse(400, "Bad Request", "Validation Error", errors);
    }
}