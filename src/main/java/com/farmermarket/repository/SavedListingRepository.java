package com.farmermarket.repository;

import com.farmermarket.model.Product;
import com.farmermarket.model.SavedListing;
import com.farmermarket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SavedListingRepository
        extends JpaRepository<SavedListing, Long> {

    // =========================================
    // GET ALL SAVED LISTINGS OF A BUYER
    // =========================================
    List<SavedListing> findByBuyer(User buyer);


    // =========================================
    // CHECK IF PRODUCT IS ALREADY SAVED
    // =========================================
    Boolean existsByBuyerAndProduct(
            User buyer,
            Product product
    );


    // =========================================
    // FIND SAVED LISTING BY BUYER AND PRODUCT
    // Used when buyer removes a saved product
    // =========================================
    SavedListing findByBuyerAndProduct(
            User buyer,
            Product product
    );
}