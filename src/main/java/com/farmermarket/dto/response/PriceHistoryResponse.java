package com.farmermarket.dto.response;

import com.farmermarket.enums.Category;

import java.time.LocalDateTime;

public class PriceHistoryResponse {

    private Long id;

    private Category category;

    private Double price;

    private String district;

    private LocalDateTime createdAt;


    public PriceHistoryResponse() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }


    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }


    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
