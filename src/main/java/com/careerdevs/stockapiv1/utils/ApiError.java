package com.careerdevs.stockapiv1.utils;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

import java.net.http.HttpClient;

public class ApiError {

    public static ResponseEntity<?> genericApiError (Exception e) {

        System.out.println(e.getMessage());
        System.out.println(e.getClass());
        return ResponseEntity.internalServerError().body(e.getMessage());

    }

    public static ResponseEntity<?> customApiError (String message, int status)  {

        return ResponseEntity.status(status).body(message);

    }

    public static void throwError (int status, String message) throws HttpClientErrorException {

        throw new HttpClientErrorException(HttpStatus.valueOf(status), message);

    }

}
