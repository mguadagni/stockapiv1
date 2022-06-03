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
    public ResponseEntity<?> getOverviewBySymbol (RestTemplate restTemplate, @PathVariable String symbol) {

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
    public ResponseEntity<?> uploadOverviewBySymbol (RestTemplate restTemplate, @PathVariable String symbol) {

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

    @GetMapping("/all")
    private ResponseEntity<?> getAllOverviews () {

        try {

            Iterable<Overview> allOverviews = overviewRepository.findAll();

            return ResponseEntity.ok(allOverviews);

        } catch (HttpClientErrorException e) {

            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

    @GetMapping("/id/{id}")
    private ResponseEntity<?> getOverviewById (@PathVariable String id) {

        try {

            Optional<Overview> foundOverview = overviewRepository.findById(Long.parseLong(id));

            if (foundOverview.isEmpty()) {

                ApiError.throwError(404, id + "did not match any overview");

            }

            return ResponseEntity.ok(foundOverview);

        } catch (HttpClientErrorException e) {

            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (NumberFormatException e) {

            return ApiError.customApiError("Invalid ID: Must be a number: " + id, 400);

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

    @GetMapping("/symbol/{symbol}")
    private ResponseEntity<?> getOverviewBySymbol (@PathVariable String symbol) {

        try {

            Optional<Overview> foundOverview = overviewRepository.findBySymbol(symbol);

            if (foundOverview.isEmpty()) {

                ApiError.throwError(404, symbol + "did not match any overview");

            }

            return ResponseEntity.ok(foundOverview);

        } catch (HttpClientErrorException e) {

            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

    @GetMapping("/exchange/{exchange}")
    private ResponseEntity<?> getOverviewByExchange (@PathVariable String exchange) {

        try {

            List<Overview> foundOverview = overviewRepository.findByExchange(exchange);

            if (foundOverview.isEmpty()) {

                ApiError.throwError(404, exchange + "did not match any overview");

            }

            return ResponseEntity.ok(foundOverview);

        } catch (HttpClientErrorException e) {

            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

    @GetMapping("/assetType/{assetType}")
    private ResponseEntity<?> getOverviewByAssetType (@PathVariable String assetType) {

        try {

            List<Overview> foundOverview = overviewRepository.findByAssetType(assetType);

            if (foundOverview.isEmpty()) {

                ApiError.throwError(404, assetType + "did not match any overview");

            }

            return ResponseEntity.ok(foundOverview);

        } catch (HttpClientErrorException e) {

            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

    @GetMapping("/name/{name}")
    private ResponseEntity<?> getOverviewByName (@PathVariable String name) {

        try {

            Optional<Overview> foundOverview = overviewRepository.findByName(name);

            if (foundOverview.isEmpty()) {

                ApiError.throwError(404, name + "did not match any overview");

            }

            return ResponseEntity.ok(foundOverview);

        } catch (HttpClientErrorException e) {

            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

    @GetMapping("/currency/{currency}")
    private ResponseEntity<?> getOverviewByCurrency (@PathVariable String currency) {

        try {

            List<Overview> foundOverview = overviewRepository.findByCurrency(currency);

            if (foundOverview.isEmpty()) {

                ApiError.throwError(404, currency + "did not match any overview");

            }

            return ResponseEntity.ok(foundOverview);

        } catch (HttpClientErrorException e) {

            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

    @GetMapping("/country/{country}")
    private ResponseEntity<?> getOverviewByCountry (@PathVariable String country) {

        try {

            List<Overview> foundOverview = overviewRepository.findByCountry(country);

            if (foundOverview.isEmpty()) {

                ApiError.throwError(404, country + "did not match any overview");

            }

            return ResponseEntity.ok(foundOverview);

        } catch (HttpClientErrorException e) {

            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

    @GetMapping("/sector/{sector}")
    private ResponseEntity<?> getOverviewBySector (@PathVariable String sector) {

        try {

            List<Overview> foundOverview = overviewRepository.findBySector(sector);

            if (foundOverview.isEmpty()) {

                ApiError.throwError(404, sector + "did not match any overview");

            }

            return ResponseEntity.ok(foundOverview);

        } catch (HttpClientErrorException e) {

            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

    @DeleteMapping("/all")
    private ResponseEntity<?> deleteAllOverviews () {

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

    @DeleteMapping("/id/{id}")
    private ResponseEntity<?> deleteById (@PathVariable String id) {

        try {

            long overViewId = Long.parseLong(id);

            Optional<Overview> foundOverview = overviewRepository.findById(Long.parseLong(id));

            if (foundOverview.isEmpty()) {

                ApiError.throwError(404, id + "did not match any overview");

            }

            overviewRepository.deleteById(overViewId);

            return ResponseEntity.ok(foundOverview);

        } catch (HttpClientErrorException e) {

            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (NumberFormatException e) {

            return ApiError.customApiError("Invalid ID: Must be a number: " + id, 400);

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

    @DeleteMapping("/symbol/{symbol}")
    private ResponseEntity<?> deleteBySymbol (@PathVariable String symbol) {

        try {

            Optional<Overview> foundOverview = overviewRepository.findBySymbol(symbol);

            if (foundOverview.isEmpty()) {

                ApiError.throwError(404, symbol + " did not match any overview");

            }

            overviewRepository.deleteBySymbol(symbol);

            return ResponseEntity.ok(foundOverview);

        } catch (HttpClientErrorException e) {

            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

    @DeleteMapping("/exchange/{exchange}")
    private ResponseEntity<?> deleteByExchange (@PathVariable String exchange) {

        try {

            List<Overview> foundOverview = overviewRepository.findByExchange(exchange);

            if (foundOverview.isEmpty()) {

                ApiError.throwError(404, exchange + " did not match any overview");

            }

            overviewRepository.deleteByExchange(exchange);

            return ResponseEntity.ok(foundOverview);

        } catch (HttpClientErrorException e) {

            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

}
