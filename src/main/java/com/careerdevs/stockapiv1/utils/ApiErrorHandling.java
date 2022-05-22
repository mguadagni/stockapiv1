package com.careerdevs.stockapiv1.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ApiErrorHandling {

    public static ResponseEntity<?> genericApiError (Exception e) {

        System.out.println(e.getMessage());
        System.out.println(e.getClass());
        return ResponseEntity.internalServerError().body(e.getMessage());

    }

    public static ResponseEntity<?> customApiError (String message, HttpStatus status)  {

        return ResponseEntity.status(status).body(message);

    }

}
