package com.farmermarket.controller;

import com.farmermarket.dto.request.SavedListingRequest;
import com.farmermarket.dto.response.SavedListingResponse;
import com.farmermarket.service.SavedListingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-listings")
public class SavedListingController {

    private final SavedListingService savedListingService;

    public SavedListingController(
            SavedListingService savedListingService) {

        this.savedListingService =
                savedListingService;
    }


    // =========================================
    // API 1 — SAVE PRODUCT
    // POST /api/saved-listings
    // =========================================

    @PostMapping
    public ResponseEntity<SavedListingResponse>
            saveListing(
                    @RequestBody @Valid
                    SavedListingRequest request) {

        SavedListingResponse response =
                savedListingService
                        .saveListing(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }


    // =========================================
    // API 2 — GET SAVED LISTINGS BY BUYER
    // GET /api/saved-listings/buyer/1
    // =========================================

    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<SavedListingResponse>>
            getSavedListingsByBuyer(
                    @PathVariable Long buyerId) {

        List<SavedListingResponse> responses =
                savedListingService
                        .getSavedListingsByBuyer(
                                buyerId
                        );

        return ResponseEntity.ok(responses);
    }


    // =========================================
    // API 3 — REMOVE SAVED PRODUCT
    // DELETE
    // /api/saved-listings/buyer/1/product/5
    // =========================================

    @DeleteMapping(
            "/buyer/{buyerId}/product/{productId}"
    )
    public ResponseEntity<String>
            removeSavedListing(
                    @PathVariable Long buyerId,
                    @PathVariable Long productId) {

        savedListingService
                .removeSavedListing(
                        buyerId,
                        productId
                );

        return ResponseEntity.ok(
                "Saved listing removed successfully!"
        );
    }
}