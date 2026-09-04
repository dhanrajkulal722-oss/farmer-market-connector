package com.farmermarket.dto.request;

import jakarta.validation.constraints.NotNull;

public class SavedListingRequest {

    @NotNull(message = "Buyer ID is required")
    private Long buyerId;

    @NotNull(message = "Product ID is required")
    private Long productId;

    public SavedListingRequest() {
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
