package com.farmermarket.service;

import com.farmermarket.dto.request.PriceHistoryRequest;
import com.farmermarket.dto.response.PriceHistoryResponse;
import com.farmermarket.enums.Category;
import com.farmermarket.exception.ResourceNotFoundException;
import com.farmermarket.model.PriceHistory;
import com.farmermarket.repository.PriceHistoryRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PriceHistoryService {

    private final PriceHistoryRepository priceHistoryRepository;


    public PriceHistoryService(
            PriceHistoryRepository priceHistoryRepository) {

        this.priceHistoryRepository =
                priceHistoryRepository;
    }


    // =========================================
    // ADD PRICE RECORD
    // =========================================

    public PriceHistoryResponse addPrice(
            PriceHistoryRequest request) {

        PriceHistory priceHistory =
                new PriceHistory();

        priceHistory.setCategory(
                request.getCategory()
        );

        priceHistory.setPrice(
                request.getPrice()
        );

        priceHistory.setDistrict(
                request.getDistrict()
        );

        PriceHistory savedPriceHistory =
                priceHistoryRepository
                        .save(priceHistory);

        return mapToResponse(
                savedPriceHistory
        );
    }


    // =========================================
    // GET ALL PRICE HISTORY
    // =========================================

    public List<PriceHistoryResponse>
            getAllPriceHistory() {

        List<PriceHistory> priceHistories =
                priceHistoryRepository.findAll();

        List<PriceHistoryResponse> responses =
                new ArrayList<>();

        for (PriceHistory priceHistory
                : priceHistories) {

            responses.add(
                    mapToResponse(priceHistory)
            );
        }

        return responses;
    }


    // =========================================
    // GET PRICE HISTORY BY ID
    // =========================================

    public PriceHistoryResponse
            getPriceHistoryById(Long id) {

        PriceHistory priceHistory =
                priceHistoryRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Price history",
                                        "id",
                                        id
                                )
                        );

        return mapToResponse(priceHistory);
    }


    // =========================================
    // GET BY CATEGORY
    // =========================================

    public List<PriceHistoryResponse>
            getPriceHistoryByCategory(
                    Category category) {

        List<PriceHistory> priceHistories =
                priceHistoryRepository
                        .findByCategory(category);

        return mapToResponseList(
                priceHistories
        );
    }


    // =========================================
    // GET BY DISTRICT
    // =========================================

    public List<PriceHistoryResponse>
            getPriceHistoryByDistrict(
                    String district) {

        List<PriceHistory> priceHistories =
                priceHistoryRepository
                        .findByDistrict(district);

        return mapToResponseList(
                priceHistories
        );
    }


    // =========================================
    // GET BY CATEGORY + DISTRICT
    // =========================================

    public List<PriceHistoryResponse>
            getPriceHistoryByCategoryAndDistrict(
                    Category category,
                    String district) {

        List<PriceHistory> priceHistories =
                priceHistoryRepository
                        .findByCategoryAndDistrict(
                                category,
                                district
                        );

        return mapToResponseList(
                priceHistories
        );
    }


    // =========================================
    // DELETE PRICE RECORD
    // =========================================

    public void deletePriceHistory(Long id) {

        PriceHistory priceHistory =
                priceHistoryRepository
                        .findById(id)
                        .orElseThrow(() ->
                                new ResourceNotFoundException(
                                        "Price history",
                                        "id",
                                        id
                                )
                        );

        priceHistoryRepository.delete(
                priceHistory
        );
    }


    // =========================================
    // MAP ENTITY → RESPONSE DTO
    // =========================================

    private PriceHistoryResponse
            mapToResponse(
                    PriceHistory priceHistory) {

        PriceHistoryResponse response =
                new PriceHistoryResponse();

        response.setId(
                priceHistory.getId()
        );

        response.setCategory(
                priceHistory.getCategory()
        );

        response.setPrice(
                priceHistory.getPrice()
        );

        response.setDistrict(
                priceHistory.getDistrict()
        );

        response.setCreatedAt(
                priceHistory.getCreatedAt()
        );

        return response;
    }


    // =========================================
    // MAP ENTITY LIST → RESPONSE LIST
    // =========================================

    private List<PriceHistoryResponse>
            mapToResponseList(
                    List<PriceHistory> priceHistories) {

        List<PriceHistoryResponse> responses =
                new ArrayList<>();

        for (PriceHistory priceHistory
                : priceHistories) {

            responses.add(
                    mapToResponse(priceHistory)
            );
        }

        return responses;
    }
}
