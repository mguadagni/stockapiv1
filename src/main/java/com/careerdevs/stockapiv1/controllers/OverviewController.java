package com.careerdevs.stockapiv1.controllers;

import com.careerdevs.stockapiv1.utils.ApiErrorHandling;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Objects;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    private Environment env;

    private final String BASE_URL = "https://www.alphavantage.co/query?function=OVERVIEW";

    @GetMapping("/test")
    public ResponseEntity<?> testOverview (RestTemplate restTemplate) {

        try {

            String url = BASE_URL + "&symbol=IBM&apikey=demo";

            String alphaVantageResponse = restTemplate.getForObject(url, String.class);

            return ResponseEntity.ok(alphaVantageResponse);

        } catch (Exception e) {

            return ApiErrorHandling.genericApiError(e);

        }

    }

    @GetMapping("/{symbol}")
    public ResponseEntity<?> getOverviewBySymbol (RestTemplate restTemplate, @PathVariable String symbol) {

        try {

            String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + env.getProperty("AV_API_KEY");

            String alphaVantageResponse = restTemplate.getForObject(url, String.class);

            if (alphaVantageResponse == null) {

                return ApiErrorHandling.customApiError("Did not receive response",
                        HttpStatus.INTERNAL_SERVER_ERROR);

            } else if (alphaVantageResponse.equals("{}")) {

                return ApiErrorHandling.customApiError("Invalid Stock Symbol: " + symbol,
                        HttpStatus.BAD_REQUEST);

            }

            return ResponseEntity.ok(alphaVantageResponse);

        } catch (Exception e) {

            return ApiErrorHandling.genericApiError(e);

        }

    }

}
