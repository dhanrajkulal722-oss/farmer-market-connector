package com.farmermarket.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

/**
 * What buyer SENDS to place order
 *
 * Suresh sends:
 * {
 *   "productId": 1,
 *   "buyerId": 2,
 *   "quantity": 200,
 *   "message": "Need by Sunday!"
 * }
 */
public class OrderRequest {

    /**
     * Which product/listing?
     * Suresh interested in
     * listing id = 1 (coconuts)
     */
    @NotNull(message = "Product id is required")
    private Long productId;

    /**
     * Who is placing order?
     * Suresh's user id = 2
     */
    @NotNull(message = "Buyer id is required")
    private Long buyerId;

    /**
     * How many does buyer want?
     * 200 coconuts
     */
    @NotNull(message = "Quantity is required")
    @Min(
        value = 1,
        message = "Quantity must be at least 1")
    private Double quantity;

    /**
     * Optional message to farmer
     * "Need by this Sunday!"
     * "Can you deliver to Mangalore?"
     */
    private String message;

    // ════════════════════════════
    // CONSTRUCTOR
    // ════════════════════════════
    public OrderRequest() {
    }

    // ════════════════════════════
    // GETTERS
    // ════════════════════════════

    public Long getProductId() {
        return productId;
    }

    public Long getBuyerId() {
        return buyerId;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getMessage() {
        return message;
    }

    // ════════════════════════════
    // SETTERS
    // ════════════════════════════

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public void setBuyerId(Long buyerId) {
        this.buyerId = buyerId;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
