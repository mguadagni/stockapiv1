package com.careerdevs.stockapiv1.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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

            String responseBody = restTemplate.getForObject(url, String.class);

            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {

            return ResponseEntity.internalServerError().body(e.getMessage());

        }

    }

    @GetMapping("/{symbol}")
    public ResponseEntity<?> dynamicOverview (RestTemplate restTemplate, @PathVariable String symbol) {

        try {

            String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + env.getProperty("AV_API_KEY");

            String responseBody = restTemplate.getForObject(url, String.class);

            return ResponseEntity.ok(responseBody);

        } catch (Exception e) {

            return ResponseEntity.internalServerError().body(e.getMessage());

        }

    }

}
