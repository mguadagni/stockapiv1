package com.careerdevs.stockapiv1.controllers;

import com.careerdevs.stockapiv1.models.Overview;
import com.careerdevs.stockapiv1.repositories.OverviewRepository;
import com.careerdevs.stockapiv1.utils.ApiError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    private Environment env;

    @Autowired
    private OverviewRepository overviewRepository;

    private final String BASE_URL = "https://www.alphavantage.co/query?function=OVERVIEW";

    @GetMapping("/{symbol}")
    public ResponseEntity<?> getOverviewBySymbol(RestTemplate restTemplate, @PathVariable String symbol) {

        try {

            String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + env.getProperty("AV_API_KEY");

            Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);

            if (alphaVantageResponse == null) {

                ApiError.throwError(500, "Did not receive response from AV");

            } else if (alphaVantageResponse.getSymbol() == null) {

                ApiError.throwError(404, "Invalid Stock Symbol: " + symbol);

            }

            return ResponseEntity.ok(alphaVantageResponse);

        } catch (HttpClientErrorException e) {

            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

    @PostMapping("/{symbol}")
    public ResponseEntity<?> uploadOverviewBySymbol(RestTemplate restTemplate, @PathVariable String symbol) {

        try {

            String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + env.getProperty("AV_API_KEY");

            Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);

            if (alphaVantageResponse == null) {

                ApiError.throwError(500, "Did not receive response from AV");

            } else if (alphaVantageResponse.getSymbol() == null) {

                ApiError.throwError(404, "Invalid Stock Symbol: " + symbol);

            }

            Overview savedOverview = overviewRepository.save(alphaVantageResponse);

            return ResponseEntity.ok(savedOverview);

        } catch (HttpClientErrorException e) {

            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (DataIntegrityViolationException e) {

            return ApiError.customApiError("Can not upload duplicate Stock data",
                    400);

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

    @PostMapping("/all")
    public ResponseEntity<?> uploadTestOverviews(RestTemplate restTemplate) {

        try {

            String[] testSymbols = {"AAPL", "IBM", "GOOGL", "GOOG", "TM"};

            ArrayList<Overview> overviews = new ArrayList<>();

            for (int i = 0; i < testSymbols.length; i++) {

                String symbol = testSymbols[i];

                String apiKey = env.getProperty("AV_API_KEY");
                String url = BASE_URL + "&symbol=" + symbol + "&apikey=" + apiKey;

                Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);

                if (alphaVantageResponse == null) {
                    ApiError.throwError(500, "Did not receive response from AV");
                } else if (alphaVantageResponse.getSymbol() == null) {
                    ApiError.throwError(404, "Invalid Stock Symbol: " + symbol);
                }

                overviews.add(alphaVantageResponse);

            }

            Iterable<Overview> savedOverview = overviewRepository.saveAll(overviews);

            return ResponseEntity.ok(savedOverview);

        } catch (HttpClientErrorException e) {

            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (DataIntegrityViolationException e) {

            return ApiError.customApiError("Can not upload duplicate Stock data",
                    400);

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

    @GetMapping("/all")
    private ResponseEntity<?> getAllOverviews() {

        try {

            Iterable<Overview> allOverviews = overviewRepository.findAll();

            return ResponseEntity.ok(allOverviews);

        } catch (HttpClientErrorException e) {

            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

//    @GetMapping("/id/{id}")
//    private ResponseEntity<?> getOverviewById (@PathVariable String id) {
//
//        try {
//
//            List<Overview> foundOverview = overviewRepository.findById(Long.parseLong(id));
//
//            if (foundOverview.isEmpty()) {
//                ApiError.throwError(404, id + "did not match any overview");
//            }
//
//            return ResponseEntity.ok(foundOverview);
//
//        } catch (HttpClientErrorException e) {
//            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
//        } catch (NumberFormatException e) {
//            return ApiError.customApiError("Invalid ID: Must be a number: " + id, 400);
//        } catch (Exception e) {
//            return ApiError.genericApiError(e);
//        }
//
//    }

    @DeleteMapping("/all")
    private ResponseEntity<?> deleteAllOverviews() {

        try {

            long allOverviewsCount = overviewRepository.count();

            if (allOverviewsCount == 0) return ResponseEntity.ok("No Overviews to delete");

            overviewRepository.deleteAll();

            return ResponseEntity.ok("Deleted Overviews: " + allOverviewsCount);

        } catch (HttpClientErrorException e) {

            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

//    @DeleteMapping("/id/{id}")
//    private ResponseEntity<?> deleteById (@PathVariable String id) {
//
//        try {
//
//            long overViewId = Long.parseLong(id);
//
//            List<Overview> foundOverview = overviewRepository.findById(overViewId);
//
//            if (foundOverview.isEmpty()) {
//
//                ApiError.throwError(404, id + "did not match any overview");
//
//            }
//
//            overviewRepository.deleteById(overViewId);
//
//            return ResponseEntity.ok(foundOverview);
//
//        } catch (HttpClientErrorException e) {
//
//            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
//
//        } catch (NumberFormatException e) {
//
//            return ApiError.customApiError("Invalid ID: Must be a number: " + id, 400);
//
//        } catch (Exception e) {
//
//            return ApiError.genericApiError(e);
//
//        }
//
//    }

//    @DeleteMapping("/symbol/{symbol}")
//    private ResponseEntity<?> deleteBySymbol (@PathVariable String symbol) {
//
//        try {
//
////            List<Overview> foundOverview = overviewRepository.findBySymbol(symbol);
////
////            if (foundOverview.isEmpty()) {
////                ApiError.throwError(404, symbol + "did not match any overview");
////            }
//
//            List<Overview> foundOverview = overviewRepository.deleteBySymbol(symbol);
//
//            return ResponseEntity.ok(foundOverview);
//
//        } catch (HttpClientErrorException e) {
//            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
//
//        } catch (Exception e) {
//            return ApiError.genericApiError(e);
//        }
//
//    }

    @GetMapping("/{field}/{value}")
    private ResponseEntity<?> getOverviewByField (@PathVariable String field, @PathVariable String value) {

        try {

            List<Overview> foundOverview = null;

            field = field.toLowerCase();

            switch (field) {
                case "id" -> foundOverview = overviewRepository.findById(Long.parseLong(value));
                case "symbol" -> foundOverview = overviewRepository.findBySymbol(value);
                case "sector" -> foundOverview = overviewRepository.findBySector(value);
                case "name" -> foundOverview = overviewRepository.findByName(value);
                case "currency" -> foundOverview = overviewRepository.findByCurrency(value);
                case "country" -> foundOverview = overviewRepository.findByCountry(value);
                case "marketcapgte" -> foundOverview =
                        overviewRepository.findByMarketCapGreaterThanEqual(Long.parseLong(value));
                case "marketcaplte" -> foundOverview =
                        overviewRepository.findByMarketCapLessThanEqual(Long.parseLong(value));
            }

            if (foundOverview == null || foundOverview.isEmpty()) {
                ApiError.throwError(404, field + "did not match any overview");
            }

            return ResponseEntity.ok(foundOverview);

        } catch (HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        } catch (NumberFormatException e) {
            return ApiError.customApiError("Invalid ID: Must be a number: " + field, 400);
        } catch (Exception e) {
            return ApiError.genericApiError(e);
        }

    }

    @DeleteMapping("/{field}/{value}")
    private ResponseEntity<?> deleteOverviewByField (@PathVariable String field, @PathVariable String value) {

        try {

            List<Overview> foundOverview = null;

            field = field.toLowerCase();

            switch (field) {
                case "id" -> foundOverview = overviewRepository.deleteById(Long.parseLong(value));
                case "symbol" -> foundOverview = overviewRepository.deleteBySymbol(value);
                case "sector" -> foundOverview = overviewRepository.deleteBySector(value);
                case "name" -> foundOverview = overviewRepository.deleteByName(value);
                case "currency" -> foundOverview = overviewRepository.deleteByCurrency(value);
                case "country" -> foundOverview = overviewRepository.deleteByCountry(value);
                case "marketcapgte" -> foundOverview =
                        overviewRepository.deleteByMarketCapGreaterThanEqual(Long.parseLong(value));
                case "marketcaplte" -> foundOverview =
                        overviewRepository.deleteByMarketCapLessThanEqual(Long.parseLong(value));
            }

            if (foundOverview == null || foundOverview.isEmpty()) {
                ApiError.throwError(404, field + "did not match any overview");
            }

            return ResponseEntity.ok(foundOverview);

        } catch (HttpClientErrorException e) {
            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());
        } catch (NumberFormatException e) {
            return ApiError.customApiError("Invalid ID: Must be a number: " + field, 400);
        } catch (Exception e) {
            return ApiError.genericApiError(e);
        }

    }

}
//AssetType, Exchange, 52WeekHigh, 52WeekLow, Industry, DividendDate