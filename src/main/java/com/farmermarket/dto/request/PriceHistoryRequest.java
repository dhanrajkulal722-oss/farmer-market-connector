package com.farmermarket.dto.request;

import com.farmermarket.enums.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class PriceHistoryRequest {

    @NotNull(message = "Category is required")
    private Category category;

    @NotNull(message = "Price is required")
    @Positive(message = "Price must be greater than zero")
    private Double price;

    @NotNull(message = "District is required")
    private String district;


    public PriceHistoryRequest() {
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
}
