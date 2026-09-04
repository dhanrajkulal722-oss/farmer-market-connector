package com.farmermarket.dto.request;

import com.farmermarket.enums.Category;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/**
 * What farmer SENDS to create listing
 *
 * Farmer sends:
 * {
 *   "farmerId": 1,
 *   "name": "Fresh Coconuts",
 *   "category": "COCONUT",
 *   "quantity": 500,
 *   "unit": "pieces",
 *   "price": 18.0,
 *   "location": "Puttur",
 *   "district": "Dakshina Kannada",
 *   "description": "Good quality"
 * }
 */
public class ProductRequest {

    /**
     * Which farmer is posting?
     * Must provide farmer id!
     */
    @NotNull(
        message = "Farmer id is required")
    private Long farmerId;

    /**
     * Product name
     * "Fresh Coconuts"
     * "Premium Arecanut"
     */
    @NotBlank(
        message = "Product name is required")
    @Size(
        min = 2,
        max = 100,
        message = "Name must be 2 to 100 characters")
    private String name;

    /**
     * Category of produce
     * COCONUT, ARECANUT, CASHEW etc
     */
    @NotNull(
        message = "Category is required")
    private Category category;

    /**
     * How much is available?
     * 500 coconuts
     * 100 kg pepper
     */
    @NotNull(
        message = "Quantity is required")
    @Min(
        value = 1,
        message = "Quantity must be at least 1")
    private Double quantity;

    /**
     * Unit of measurement
     * "pieces", "kg", "quintal"
     */
    @NotBlank(
        message = "Unit is required")
    private String unit;

    /**
     * Price per unit in rupees
     * 18.0 means ₹18 per piece
     */
    @NotNull(
        message = "Price is required")
    @Min(
        value = 1,
        message = "Price must be at least 1")
    private Double price;

    /**
     * Where is produce available?
     * "Puttur", "Bantwal"
     */
    private String location;

    /**
     * Which district?
     * "Dakshina Kannada"
     */
    private String district;

    /**
     * Optional description
     * "Freshly harvested,
     *  good quality"
     */
    private String description;

    // ════════════════════════════
    // CONSTRUCTOR
    // ════════════════════════════
    public ProductRequest() {
    }

    // ════════════════════════════
    // GETTERS
    // ════════════════════════════

    public Long getFarmerId() {
        return farmerId;
    }

    public String getName() {
        return name;
    }

    public Category getCategory() {
        return category;
    }

    public Double getQuantity() {
        return quantity;
    }

    public String getUnit() {
        return unit;
    }

    public Double getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public String getDistrict() {
        return district;
    }

    public String getDescription() {
        return description;
    }

    // ════════════════════════════
    // SETTERS
    // ════════════════════════════

    public void setFarmerId(Long farmerId) {
        this.farmerId = farmerId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(
            Category category) {
        this.category = category;
    }

    public void setQuantity(
            Double quantity) {
        this.quantity = quantity;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public void setLocation(
            String location) {
        this.location = location;
    }

    public void setDistrict(
            String district) {
        this.district = district;
    }

    public void setDescription(
            String description) {
        this.description = description;
    }
}