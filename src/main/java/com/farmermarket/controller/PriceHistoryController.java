package com.farmermarket.controller;

import com.farmermarket.dto.request.PriceHistoryRequest;
import com.farmermarket.dto.response.PriceHistoryResponse;
import com.farmermarket.enums.Category;
import com.farmermarket.service.PriceHistoryService;

import jakarta.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/price-history")
public class PriceHistoryController {

    private final PriceHistoryService priceHistoryService;

    public PriceHistoryController(
            PriceHistoryService priceHistoryService) {

        this.priceHistoryService =
                priceHistoryService;
    }


    // =========================================
    // API 1 — ADD PRICE RECORD
    //
    // POST /api/price-history
    // =========================================

    @PostMapping
    public ResponseEntity<PriceHistoryResponse>
            addPriceHistory(

                    @RequestBody
                    @Valid
                    PriceHistoryRequest request) {

        PriceHistoryResponse response =
                priceHistoryService
                        .addPrice(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // =========================================
    // API 2 — GET ALL PRICE HISTORY
    //
    // GET /api/price-history
    // =========================================

    @GetMapping
    public ResponseEntity<
            List<PriceHistoryResponse>>
            getAllPriceHistory() {

        List<PriceHistoryResponse> responses =
                priceHistoryService
                        .getAllPriceHistory();

        return ResponseEntity.ok(responses);
    }


    // =========================================
    // API 3 — GET PRICE HISTORY BY ID
    //
    // GET /api/price-history/1
    // =========================================

    @GetMapping("/{id}")
    public ResponseEntity<PriceHistoryResponse>
            getPriceHistoryById(

                    @PathVariable Long id) {

        PriceHistoryResponse response =
                priceHistoryService
                        .getPriceHistoryById(id);

        return ResponseEntity.ok(response);
    }


    // =========================================
    // API 4 — GET BY CATEGORY
    //
    // GET /api/price-history/category/COCONUT
    // =========================================

    @GetMapping("/category/{category}")
    public ResponseEntity<
            List<PriceHistoryResponse>>
            getPriceHistoryByCategory(

                    @PathVariable Category category) {

        List<PriceHistoryResponse> responses =
                priceHistoryService
                        .getPriceHistoryByCategory(
                                category
                        );

        return ResponseEntity.ok(responses);
    }


    // =========================================
    // API 5 — GET BY DISTRICT
    //
    // GET /api/price-history/district/UDUPI
    // =========================================

    @GetMapping("/district/{district}")
    public ResponseEntity<
            List<PriceHistoryResponse>>
            getPriceHistoryByDistrict(

                    @PathVariable String district) {

        List<PriceHistoryResponse> responses =
                priceHistoryService
                        .getPriceHistoryByDistrict(
                                district
                        );

        return ResponseEntity.ok(responses);
    }


    // =========================================
    // API 6 — GET BY CATEGORY + DISTRICT
    //
    // GET
    // /api/price-history/category/COCONUT/district/UDUPI
    // =========================================

    @GetMapping(
            "/category/{category}/district/{district}"
    )
    public ResponseEntity<
            List<PriceHistoryResponse>>
            getPriceHistoryByCategoryAndDistrict(

                    @PathVariable Category category,

                    @PathVariable String district) {

        List<PriceHistoryResponse> responses =
                priceHistoryService
                        .getPriceHistoryByCategoryAndDistrict(
                                category,
                                district
                        );

        return ResponseEntity.ok(responses);
    }


    // =========================================
    // API 7 — DELETE PRICE RECORD
    //
    // DELETE /api/price-history/1
    // =========================================

    @DeleteMapping("/{id}")
    public ResponseEntity<String>
            deletePriceHistory(

                    @PathVariable Long id) {

        priceHistoryService
                .deletePriceHistory(id);

        return ResponseEntity.ok(
                "Price history deleted successfully!"
        );
    }
}