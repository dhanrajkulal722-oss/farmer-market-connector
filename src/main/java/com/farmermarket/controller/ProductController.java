package com.farmermarket.controller;

import com.farmermarket.dto.request.ProductRequest;
import com.farmermarket.dto.response.ProductResponse;
import com.farmermarket.enums.Category;
import com.farmermarket.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(
            ProductService productService) {
        this.productService = productService;
    }

    // ════════════════════════════
    // API 1 — CREATE PRODUCT
    // POST /api/products
    // ════════════════════════════
    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @RequestBody @Valid
            ProductRequest request) {

        ProductResponse response =
                productService.createProduct(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ════════════════════════════
    // API 2 — GET ALL PRODUCTS
    // GET /api/products
    // ════════════════════════════
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAllProducts() {

        List<ProductResponse> products =
                productService.getAllProducts();

        return ResponseEntity.ok(products);
    }

    // ════════════════════════════
    // API 3 — GET PRODUCT BY ID
    // GET /api/products/1
    // ════════════════════════════
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponse> getProductById(
            @PathVariable Long id) {

        ProductResponse response =
                productService.getProductById(id);

        return ResponseEntity.ok(response);
    }

    // ════════════════════════════
    // API 4 — GET BY FARMER
    // GET /api/products/farmer/1
    // ════════════════════════════
    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<List<ProductResponse>> getProductsByFarmer(
            @PathVariable Long farmerId) {

        List<ProductResponse> products =
                productService.getProductsByFarmer(farmerId);

        return ResponseEntity.ok(products);
    }

    // ════════════════════════════
    // API 5 — GET BY CATEGORY
    // GET /api/products/category/COCONUT
    // ════════════════════════════
    @GetMapping("/category/{category}")
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @PathVariable Category category) {

        List<ProductResponse> products =
                productService.getProductsByCategory(category);

        return ResponseEntity.ok(products);
    }

    // ════════════════════════════
    // API 6 — UPDATE PRODUCT
    // PUT /api/products/1
    // ════════════════════════════
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> updateProduct(
            @PathVariable Long id,
            @RequestBody ProductRequest request) {

        ProductResponse response =
                productService.updateProduct(id, request);

        return ResponseEntity.ok(response);
    }

    // ════════════════════════════
    // API 7 — DELETE PRODUCT
    // DELETE /api/products/1
    // ════════════════════════════
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(
            @PathVariable Long id) {

        productService.deleteProduct(id);

        return ResponseEntity
                .ok("Product deleted successfully!");
    }
}