package com.farmermarket.service;

import com.farmermarket.dto.request.ProductRequest;
import com.farmermarket.dto.response.ProductResponse;
import com.farmermarket.enums.Category;
import com.farmermarket.enums.ProductStatus;
import com.farmermarket.enums.Role;
import com.farmermarket.exception.ResourceNotFoundException;
import com.farmermarket.model.Product;
import com.farmermarket.model.User;
import com.farmermarket.repository.ProductRepository;
import com.farmermarket.repository.UserRepository;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

/**
 * ALL business logic for products!
 *
 * Rules handled here:
 * → Only FARMER can create listing
 * → Farmer must exist in database
 * → Price must be positive
 * → Status starts as ACTIVE
 * → Map entity to response DTO
 */
@Service
public class ProductService {

    /**
     * We need TWO repositories!
     *
     * ProductRepository:
     * → Save product listing
     * → Find products
     *
     * UserRepository:
     * → Find farmer by id
     * → Check farmer exists
     * → Check role is FARMER
     */
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    /**
     * Constructor injection!
     * Spring injects both repositories!
     */
    public ProductService(ProductRepository productRepository,
            UserRepository userRepository) {
        this.productRepository =productRepository;
        this.userRepository =userRepository;
    }

    // ════════════════════════════
    // CREATE PRODUCT LISTING
    // ════════════════════════════

    /**
     * Farmer creates new listing
     *
     * Steps:
     * 1. Find farmer by id
     * 2. Check user is FARMER
     * 3. Build Product object
     * 4. Save to database
     * 5. Return ProductResponse
     */
    public ProductResponse createProduct(ProductRequest request) {

        /**
         * STEP 1:
         * Find farmer by id
         *
         * SQL:
         * SELECT * FROM users
         * WHERE id = ?
         *
         * If not found:
         * → Throw exception
         * → Return 404
         */
        User farmer = userRepository
                .findById(request.getFarmerId())
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Farmer not found " +
                        "with id: " +
                        request.getFarmerId()));

        /**
         * STEP 2:
         * Check user is FARMER!
         *
         * Business rule:
         * Only FARMER can post listings!
         * BUYER cannot post!
         * ADMIN cannot post!
         *
         * If Suresh (BUYER) tries
         * to post listing:
         * → Throw exception!
         * → "Only farmers can post!"
         */
        if (farmer.getRole() != Role.FARMER) {
            throw new RuntimeException(
                "Only farmers can " +
                "create product listings!");
        }

        /**
         * STEP 3:
         * Build Product object
         *
         * Map ProductRequest → Product
         */
        Product product = new Product();

        /**
         * Set farmer!
         * This creates the relationship!
         * product.farmer_id = ramesh.id
         * in database!
         *
         * @ManyToOne relationship!
         * Many products → One farmer!
         */
        product.setFarmer(farmer);

        product.setName(
                request.getName());
        product.setCategory(
                request.getCategory());
        product.setDescription(
                request.getDescription());
        product.setQuantity(
                request.getQuantity());
        product.setUnit(
                request.getUnit());
        product.setPrice(
                request.getPrice());
        product.setLocation(
                request.getLocation());
        product.setDistrict(
                request.getDistrict());

        /**
         * Status starts as ACTIVE!
         * Client does NOT set this!
         * We set it automatically!
         *
         * Business rule:
         * New listings are always ACTIVE!
         * Farmer manually marks as SOLD
         * when product is sold!
         */
        product.setStatus(
                ProductStatus.ACTIVE);

        /**
         * STEP 4:
         * Save to database!
         *
         * SQL generated:
         * INSERT INTO products
         * (farmer_id, name, category,
         *  description, quantity, unit,
         *  price, location, district,
         *  status, created_at)
         * VALUES
         * (1, 'Fresh Coconuts', 'COCONUT',
         *  'Good quality', 500, 'pieces',
         *  18.0, 'Puttur', 'DK',
         *  'ACTIVE', NOW())
         *
         * MySQL creates row!
         * id = 1 generated!
         */
        Product savedProduct =
                productRepository
                    .save(product);

        /**
         * STEP 5:
         * Map to ProductResponse
         * Return to controller!
         */
        return mapToResponse(savedProduct);
    }

    // ════════════════════════════
    // GET ALL PRODUCTS
    // ════════════════════════════

    /**
     * Get all product listings
     * Buyers browse all listings!
     */
    public List<ProductResponse>
            getAllProducts() {

        /**
         * findAll() from JpaRepository
         *
         * SQL:
         * SELECT * FROM products
         *
         * Returns all products!
         */
        List<Product> products =
                productRepository.findAll();

        /**
         * Convert each Product
         * to ProductResponse!
         */
        List<ProductResponse> responseList =
                new ArrayList<>();

        for (Product product : products) {
            responseList.add(
                mapToResponse(product));
        }

        return responseList;
    }

    // ════════════════════════════
    // GET PRODUCT BY ID
    // ════════════════════════════

    /**
     * Get single product by id
     */
    public ProductResponse getProductById(
            Long id) {

        /**
         * SQL:
         * SELECT * FROM products
         * WHERE id = ?
         *
         * If not found → 404!
         */
        Product product = productRepository
                .findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Product not found " +
                        "with id: " + id));

        return mapToResponse(product);
    }

    // ════════════════════════════
    // GET PRODUCTS BY FARMER
    // ════════════════════════════

    /**
     * Get all listings by one farmer
     *
     * Ramesh sees all his listings!
     */
    public List<ProductResponse>
            getProductsByFarmer(
                Long farmerId) {

        /**
         * Find farmer first!
         */
        User farmer = userRepository
                .findById(farmerId)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Farmer not found " +
                        "with id: " + farmerId));

        /**
         * Get all products by farmer!
         *
         * SQL:
         * SELECT * FROM products
         * WHERE farmer_id = ?
         */
        List<Product> products =
                productRepository
                    .findByFarmer(farmer);

        List<ProductResponse> responseList =
                new ArrayList<>();

        for (Product product : products) {
            responseList.add(
                mapToResponse(product));
        }

        return responseList;
    }

    // ════════════════════════════
    // GET PRODUCTS BY CATEGORY
    // ════════════════════════════

    /**
     * Get all listings by category
     *
     * Suresh wants only COCONUT!
     */
    public List<ProductResponse>
            getProductsByCategory(
                Category category) {

        /**
         * SQL:
         * SELECT * FROM products
         * WHERE category = ?
         */
        List<Product> products =
                productRepository
                    .findByCategory(category);

        List<ProductResponse> responseList =
                new ArrayList<>();

        for (Product product : products) {
            responseList.add(
                mapToResponse(product));
        }

        return responseList;
    }

    // ════════════════════════════
    // UPDATE PRODUCT
    // ════════════════════════════

    /**
     * Farmer updates listing
     *
     * Ramesh changes price:
     * ₹18 → ₹20
     */
    public ProductResponse updateProduct(
            Long id,
            ProductRequest request) {

        /**
         * Find product first!
         * If not found → 404!
         */
        Product product = productRepository
                .findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Product not found " +
                        "with id: " + id));

        /**
         * Update fields
         * Only update if value provided!
         */
        if (request.getName() != null) {
            product.setName(
                    request.getName());
        }

        if (request.getDescription()
                != null) {
            product.setDescription(
                    request.getDescription());
        }

        if (request.getQuantity() != null) {
            product.setQuantity(
                    request.getQuantity());
        }

        if (request.getPrice() != null) {
            product.setPrice(
                    request.getPrice());
        }

        if (request.getLocation() != null) {
            product.setLocation(
                    request.getLocation());
        }

        if (request.getDistrict() != null) {
            product.setDistrict(
                    request.getDistrict());
        }

        /**
         * Save updated product!
         *
         * product.id is NOT null!
         * JPA sees existing entity!
         * Uses UPDATE not INSERT!
         *
         * SQL:
         * UPDATE products
         * SET name=?, price=?...
         * WHERE id = ?
         */
        Product updatedProduct =
                productRepository
                    .save(product);

        return mapToResponse(updatedProduct);
    }

    // ════════════════════════════
    // DELETE PRODUCT
    // ════════════════════════════

    /**
     * Farmer deletes listing
     *
     * Ramesh sold coconuts!
     * Removes the listing!
     */
    public void deleteProduct(Long id) {

        /**
         * Check product exists!
         * If not found → 404!
         */
        Product product = productRepository
                .findById(id)
                .orElseThrow(() ->
                    new ResourceNotFoundException(
                        "Product not found " +
                        "with id: " + id));

        /**
         * Delete from database!
         *
         * SQL:
         * DELETE FROM products
         * WHERE id = ?
         */
        productRepository.delete(product);
    }

    // ════════════════════════════
    // PRIVATE HELPER METHOD
    // Map Product → ProductResponse
    // ════════════════════════════

    /**
     * Copies data from Product entity
     * to ProductResponse DTO!
     *
     * Includes farmer details!
     * So buyer can contact farmer!
     *
     * This is the KEY feature
     * of our app!
     * Direct farmer to buyer contact!
     */
    private ProductResponse mapToResponse(
            Product product) {

        ProductResponse response =
                new ProductResponse();

        /**
         * Product details
         */
        response.setId(
                product.getId());
        response.setName(
                product.getName());
        response.setCategory(
                product.getCategory());
        response.setDescription(
                product.getDescription());
        response.setQuantity(
                product.getQuantity());
        response.setUnit(
                product.getUnit());
        response.setPrice(
                product.getPrice());
        response.setLocation(
                product.getLocation());
        response.setDistrict(
                product.getDistrict());
        response.setStatus(
                product.getStatus());
        response.setCreatedAt(
                product.getCreatedAt());

        /**
         * Farmer details!
         * Get farmer from product!
         *
         * product.getFarmer()
         * → Returns User object
         *   (the farmer!)
         *
         * Then get farmer's details!
         */
        User farmer = product.getFarmer();

        response.setFarmerId(
                farmer.getId());

        /**
         * Farmer name!
         * Buyer sees "Ramesh Shetty"
         * Knows who is selling!
         */
        response.setFarmerName(
                farmer.getName());

        /**
         * Farmer phone!
         * Buyer can call Ramesh
         * directly on WhatsApp!
         * NO MIDDLEMAN!
         * This is the whole point
         * of our app!
         */
        response.setFarmerPhone(
                farmer.getPhone());

        return response;
    }
}
