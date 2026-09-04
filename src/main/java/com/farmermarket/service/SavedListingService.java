package com.farmermarket.service;

import com.farmermarket.dto.request.SavedListingRequest;
import com.farmermarket.dto.response.SavedListingResponse;
import com.farmermarket.enums.Role;
import com.farmermarket.exception.ResourceNotFoundException;
import com.farmermarket.model.Product;
import com.farmermarket.model.SavedListing;
import com.farmermarket.model.User;
import com.farmermarket.repository.ProductRepository;
import com.farmermarket.repository.SavedListingRepository;
import com.farmermarket.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SavedListingService {

    private final SavedListingRepository savedListingRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public SavedListingService(
            SavedListingRepository savedListingRepository,
            UserRepository userRepository,
            ProductRepository productRepository) {

        this.savedListingRepository =
                savedListingRepository;

        this.userRepository =
                userRepository;

        this.productRepository =
                productRepository;
    }


    // =========================================
    // SAVE PRODUCT
    // =========================================

    public SavedListingResponse saveListing(
            SavedListingRequest request) {

        // STEP 1: Find buyer
        User buyer = userRepository
                .findById(request.getBuyerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Buyer",
                                "id",
                                request.getBuyerId()
                        ));

        // STEP 2: Check user is BUYER
        if (buyer.getRole() != Role.BUYER) {

            throw new RuntimeException(
                    "Only buyers can save products!"
            );
        }

        // STEP 3: Find product
        Product product = productRepository
                .findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product",
                                "id",
                                request.getProductId()
                        ));

        // STEP 4: Check duplicate saved listing
        if (savedListingRepository
                .existsByBuyerAndProduct(
                        buyer,
                        product)) {

            throw new RuntimeException(
                    "Product already saved!"
            );
        }

        // STEP 5: Create SavedListing
        SavedListing savedListing =
                new SavedListing();

        savedListing.setBuyer(buyer);

        savedListing.setProduct(product);

        // STEP 6: Save to database
        SavedListing saved =
                savedListingRepository
                        .save(savedListing);

        // STEP 7: Return response
        return mapToResponse(saved);
    }


    // =========================================
    // GET ALL SAVED LISTINGS BY BUYER
    // =========================================

    public List<SavedListingResponse>
            getSavedListingsByBuyer(
                    Long buyerId) {

        // Find buyer
        User buyer = userRepository
                .findById(buyerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Buyer",
                                "id",
                                buyerId
                        ));

        // Get saved listings
        List<SavedListing> savedListings =
                savedListingRepository
                        .findByBuyer(buyer);

        // Convert entity list to response list
        List<SavedListingResponse> responses =
                new ArrayList<>();

        for (SavedListing savedListing
                : savedListings) {

            responses.add(
                    mapToResponse(savedListing)
            );
        }

        return responses;
    }


    // =========================================
    // REMOVE SAVED LISTING
    // =========================================

    public void removeSavedListing(
            Long buyerId,
            Long productId) {

        // Find buyer
        User buyer = userRepository
                .findById(buyerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Buyer",
                                "id",
                                buyerId
                        ));

        // Find product
        Product product = productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Product",
                                "id",
                                productId
                        ));

        // Find saved listing
        SavedListing savedListing =
                savedListingRepository
                        .findByBuyerAndProduct(
                                buyer,
                                product
                        );

        if (savedListing == null) {

            throw new ResourceNotFoundException(
                    "Saved listing not found"
            );
        }

        // Delete saved listing
        savedListingRepository
                .delete(savedListing);
    }


    // =========================================
    // MAP ENTITY → RESPONSE DTO
    // =========================================

    private SavedListingResponse
            mapToResponse(
                    SavedListing savedListing) {

        SavedListingResponse response =
                new SavedListingResponse();

        response.setId(
                savedListing.getId()
        );

        response.setBuyerId(
                savedListing
                        .getBuyer()
                        .getId()
        );

        Product product =
                savedListing.getProduct();

        response.setProductId(
                product.getId()
        );

        response.setProductName(
                product.getName()
        );

        response.setProductPrice(
                product.getPrice()
        );

        response.setProductImageUrl(
                product.getImageUrl()
        );

        response.setCreatedAt(
                savedListing.getCreatedAt()
        );

        return response;
    }
}