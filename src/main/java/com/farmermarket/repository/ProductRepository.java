package com.farmermarket.repository;

import com.farmermarket.enums.Category;
import com.farmermarket.enums.ProductStatus;
import com.farmermarket.model.Product;
import com.farmermarket.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Talks to products table!
 *
 * JpaRepository gives FREE methods:
 * save(product) → INSERT
 * findById(id) → SELECT by id
 * findAll() → SELECT all
 * deleteById(id) → DELETE
 *
 * We add custom methods below!
 * Spring generates SQL automatically
 * from method names!
 */
@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {

    /**
     * Get all listings by one farmer
     *
     * SQL generated:
     * SELECT * FROM products
     * WHERE farmer_id = ?
     *
     * Usage:
     * productRepository.findByFarmer(ramesh)
     * Returns all Ramesh's listings!
     */
    List<Product> findByFarmer(User farmer);

    /**
     * Get all listings by category
     *
     * SQL generated:
     * SELECT * FROM products
     * WHERE category = ?
     *
     * Usage:
     * productRepository
     *     .findByCategory(Category.COCONUT)
     * Returns all coconut listings!
     */
    List<Product> findByCategory(Category category);

    /**
     * Get all listings by status
     *
     * SQL generated:
     * SELECT * FROM products
     * WHERE status = ?
     *
     * Usage:
     * productRepository
     *     .findByStatus(ProductStatus.ACTIVE)
     * Returns all active listings!
     */
    List<Product> findByStatus(ProductStatus status);

    /**
     * Get all active listings
     * by category
     *
     * SQL generated:
     * SELECT * FROM products
     * WHERE category = ?
     * AND status = ?
     */
    List<Product> findByCategoryAndStatus(Category category,ProductStatus status);

    /**
     * Get all listings by district
     *
     * SQL generated:
     * SELECT * FROM products
     * WHERE district = ?
     */
    List<Product> findByDistrict(String district);

    /**
     * Get farmer listings by status
     *
     * SQL generated:
     * SELECT * FROM products
     * WHERE farmer_id = ?
     * AND status = ?
     */
    List<Product> findByFarmerAndStatus(User farmer,ProductStatus status);
}
