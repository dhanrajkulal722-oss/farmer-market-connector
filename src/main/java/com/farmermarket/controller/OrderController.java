package com.farmermarket.controller;

import com.farmermarket.dto.request.OrderRequest;
import com.farmermarket.dto.response.OrderResponse;
import com.farmermarket.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(
            OrderService orderService) {
        this.orderService = orderService;
    }

    // ════════════════════════════
    // API 1 — PLACE ORDER
    // POST /api/orders
    // Suresh shows interest!
    // ════════════════════════════
    @PostMapping
    public ResponseEntity<OrderResponse> placeOrder(
            @RequestBody @Valid
            OrderRequest request) {

        OrderResponse response =
                orderService.placeOrder(request);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    // ════════════════════════════
    // API 2 — ACCEPT ORDER
    // PUT /api/orders/1/accept/1
    //                         ↑
    //                    farmerId!
    // Only correct farmer accepts!
    // ════════════════════════════
    @PutMapping("/{orderId}/accept/{farmerId}")
    public ResponseEntity<OrderResponse> acceptOrder(
            @PathVariable Long orderId,
            @PathVariable Long farmerId) {

        OrderResponse response =
                orderService.acceptOrder(
                    orderId, farmerId);

        return ResponseEntity.ok(response);
    }

    // ════════════════════════════
    // API 3 — REJECT ORDER
    // PUT /api/orders/1/reject/1
    //                         ↑
    //                    farmerId!
    // Only correct farmer rejects!
    // ════════════════════════════
    @PutMapping("/{orderId}/reject/{farmerId}")
    public ResponseEntity<OrderResponse> rejectOrder(
            @PathVariable Long orderId,
            @PathVariable Long farmerId) {

        OrderResponse response =
                orderService.rejectOrder(
                    orderId, farmerId);

        return ResponseEntity.ok(response);
    }

    // ════════════════════════════
    // API 4 — GET FARMER ORDERS
    // GET /api/orders/farmer/1
    // Ramesh sees all interests!
    // ════════════════════════════
    @GetMapping("/farmer/{farmerId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByFarmer(
            @PathVariable Long farmerId) {

        List<OrderResponse> orders =
                orderService
                    .getOrdersByFarmer(farmerId);

        return ResponseEntity.ok(orders);
    }

    // ════════════════════════════
    // API 5 — GET BUYER ORDERS
    // GET /api/orders/buyer/2
    // Suresh sees his orders!
    // ════════════════════════════
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<OrderResponse>> getOrdersByBuyer(
            @PathVariable Long buyerId) {

        List<OrderResponse> orders =
                orderService
                    .getOrdersByBuyer(buyerId);

        return ResponseEntity.ok(orders);
    }

    // ════════════════════════════
    // API 6 — GET ORDER BY ID
    // GET /api/orders/1
    // ════════════════════════════
    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getOrderById(
            @PathVariable Long id) {

        OrderResponse response =
                orderService.getOrderById(id);

        return ResponseEntity.ok(response);
    }
}
