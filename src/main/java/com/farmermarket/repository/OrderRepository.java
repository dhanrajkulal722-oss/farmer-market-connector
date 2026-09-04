package com.farmermarket.repository;

import com.farmermarket.enums.OrderStatus;
import com.farmermarket.model.Order;
import com.farmermarket.model.Product;
import com.farmermarket.model.User;
import org.springframework.data.jpa.repository
        .JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

/**
 * Talks to orders table!
 *
 * JpaRepository gives FREE methods:
 * save(order) → INSERT or UPDATE
 * findById(id) → SELECT by id
 * findAll() → SELECT all
 * delete(order) → DELETE
 *
 * Custom methods below!
 * Spring generates SQL from names!
 */
@Repository
public interface OrderRepository
        extends JpaRepository<Order, Long> {

    /**
     * Get all orders for farmer
     *
     * Ramesh sees all interests
     * on his listings!
     *
     * SQL:
     * SELECT * FROM orders
     * WHERE farmer_id = ?
     */
    List<Order> findByFarmer(User farmer);

    /**
     * Get all orders by buyer
     *
     * Suresh sees all his
     * interest requests!
     *
     * SQL:
     * SELECT * FROM orders
     * WHERE buyer_id = ?
     */
    List<Order> findByBuyer(User buyer);

    /**
     * Get all orders for product
     *
     * How many buyers interested
     * in this listing?
     *
     * SQL:
     * SELECT * FROM orders
     * WHERE product_id = ?
     */
    List<Order> findByProduct(
            Product product);

    /**
     * Get orders by status
     *
     * SQL:
     * SELECT * FROM orders
     * WHERE status = ?
     */
    List<Order> findByStatus(
            OrderStatus status);

    /**
     * Get farmer orders by status
     *
     * Ramesh sees only PENDING orders!
     *
     * SQL:
     * SELECT * FROM orders
     * WHERE farmer_id = ?
     * AND status = ?
     */
    List<Order> findByFarmerAndStatus(
            User farmer,
            OrderStatus status);

    /**
     * Get buyer orders by status
     *
     * Suresh sees only ACCEPTED orders!
     *
     * SQL:
     * SELECT * FROM orders
     * WHERE buyer_id = ?
     * AND status = ?
     */
    List<Order> findByBuyerAndStatus(
            User buyer,
            OrderStatus status);

    /**
     * Check if buyer already
     * placed order on this product
     *
     * Prevents duplicate orders!
     *
     * SQL:
     * SELECT COUNT(*) > 0
     * FROM orders
     * WHERE product_id = ?
     * AND buyer_id = ?
     */
    Boolean existsByProductAndBuyer(
            Product product,
            User buyer);
}
