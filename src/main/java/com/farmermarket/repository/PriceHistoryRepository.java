package com.farmermarket.repository;

import com.farmermarket.enums.Category;
import com.farmermarket.model.PriceHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceHistoryRepository
        extends JpaRepository<PriceHistory, Long> {

    // =========================================
    // GET PRICE HISTORY BY CATEGORY
    // =========================================

    List<PriceHistory> findByCategory(
            Category category
    );


    // =========================================
    // GET PRICE HISTORY BY DISTRICT
    // =========================================

    List<PriceHistory> findByDistrict(
            String district
    );


    // =========================================
    // GET PRICE HISTORY BY CATEGORY + DISTRICT
    // =========================================

    List<PriceHistory> findByCategoryAndDistrict(
            Category category,
            String district
    );
}
