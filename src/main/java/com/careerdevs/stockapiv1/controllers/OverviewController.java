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

import java.util.Optional;

@RestController
@RequestMapping("/api/overview")
public class OverviewController {

    @Autowired
    private Environment env;

    @Autowired
    private OverviewRepository overviewRepository;

    private final String BASE_URL = "https://www.alphavantage.co/query?function=OVERVIEW";

    @GetMapping("/test")
    public ResponseEntity<?> testOverview (RestTemplate restTemplate) {

        try {

            String url = BASE_URL + "&symbol=IBM&apikey=demo";

            Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);

            return ResponseEntity.ok(alphaVantageResponse);

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

    @PostMapping("/test")
    public ResponseEntity<?> testUploadOverview (RestTemplate restTemplate) {

        try {

            String url = BASE_URL + "&symbol=IBM&apikey=demo";

            Overview alphaVantageResponse = restTemplate.getForObject(url, Overview.class);

            if (alphaVantageResponse == null) {

                ApiError.throwError(500, "Did not receive response from AV");

            } else if (alphaVantageResponse.getSymbol() == null) {

                ApiError.throwError(500, "No Data Retrieved From AV");

            }

            Overview savedOverview = overviewRepository.save(alphaVantageResponse);

            return ResponseEntity.ok(savedOverview);

        } catch (HttpClientErrorException e) {

            return ApiError.customApiError(e.getMessage(), e.getStatusCode().value());

        } catch(DataIntegrityViolationException e) {

            return ApiError.customApiError("Can not upload duplicate Stock data",
                    400);

        } catch (IllegalArgumentException e) {

            return ApiError.customApiError(
                    "Error in testOverview: Check URL used for AV Request",
                    500
            );

        } catch (Exception e) {

            return ApiError.genericApiError(e);

        }

    }

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

            Optional<Overview> foundOverview = overviewRepository.findById(overViewId);

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

}
