package com.farmermarket.service;

import com.farmermarket.dto.request.OrderRequest;
import com.farmermarket.dto.response.OrderResponse;
import com.farmermarket.enums.OrderStatus;
import com.farmermarket.enums.ProductStatus;
import com.farmermarket.enums.Role;
import com.farmermarket.exception.ResourceNotFoundException;
import com.farmermarket.model.Order;
import com.farmermarket.model.Product;
import com.farmermarket.model.User;
import com.farmermarket.repository.OrderRepository;
import com.farmermarket.repository.ProductRepository;
import com.farmermarket.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final NotificationService notificationService;

    public OrderService(OrderRepository orderRepository,
                        ProductRepository productRepository,
                        UserRepository userRepository,
                        NotificationService notificationService) {
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.notificationService = notificationService;
    }

    // ===============================
    // PLACE ORDER
    // ===============================
    public OrderResponse placeOrder(OrderRequest request) {

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found with id: " + request.getProductId()));

        if (product.getStatus() != ProductStatus.ACTIVE) {
            throw new RuntimeException("Product is not available!");
        }

        User buyer = userRepository.findById(request.getBuyerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Buyer not found with id: " + request.getBuyerId()));

        if (buyer.getRole() != Role.BUYER) {
            throw new RuntimeException("Only buyers can place orders!");
        }

        if (product.getFarmer().getId().equals(buyer.getId())) {
            throw new RuntimeException("Cannot order your own product!");
        }

        if (orderRepository.existsByProductAndBuyer(product, buyer)) {
            throw new RuntimeException("Already shown interest!");
        }

        Order order = new Order();
        order.setProduct(product);
        order.setBuyer(buyer);
        order.setFarmer(product.getFarmer());
        order.setQuantity(request.getQuantity());
        order.setMessage(request.getMessage());
        order.setStatus(OrderStatus.PENDING);

        Order savedOrder = orderRepository.save(order);

        // 🔥 NOTIFICATION → Farmer
        notificationService.createNotification(
                product.getFarmer(),
                "New Buyer Interest",
                buyer.getName() + " is interested in your product: " + product.getName()
        );

        return mapToResponse(savedOrder);
    }

    // ===============================
    // ACCEPT ORDER
    // ===============================
    public OrderResponse acceptOrder(Long orderId, Long farmerId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found with id: " + orderId));

        if (!order.getFarmer().getId().equals(farmerId)) {
            throw new RuntimeException("Not authorized!");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Order already processed!");
        }

        Product product = order.getProduct();

        // 🔥 STOCK CHECK
        if (product.getQuantity() < order.getQuantity()) {
            throw new RuntimeException("Not enough stock!");
        }

        // 🔥 REDUCE STOCK
        product.setQuantity(product.getQuantity() - order.getQuantity());

        if (product.getQuantity() == 0) {
            product.setStatus(ProductStatus.SOLD);
        }

        productRepository.save(product);

        // 🔥 ACCEPT ORDER
        order.setStatus(OrderStatus.ACCEPTED);
        Order updatedOrder = orderRepository.save(order);

        // 🔥 REJECT OTHER BUYERS
        List<Order> otherOrders = orderRepository.findByProduct(product);
        for (Order o : otherOrders) {
            if (!o.getId().equals(orderId) && o.getStatus() == OrderStatus.PENDING) {
                o.setStatus(OrderStatus.REJECTED);
                orderRepository.save(o);
            }
        }

        // 🔥 NOTIFICATION → Buyer
        notificationService.createNotification(
                order.getBuyer(),
                "Order Accepted",
                "Your order for " + product.getName() + " has been accepted"
        );

        return mapToResponse(updatedOrder);
    }

    // ===============================
    // REJECT ORDER
    // ===============================
    public OrderResponse rejectOrder(Long orderId, Long farmerId) {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found with id: " + orderId));

        if (!order.getFarmer().getId().equals(farmerId)) {
            throw new RuntimeException("Not authorized!");
        }

        if (order.getStatus() != OrderStatus.PENDING) {
            throw new RuntimeException("Order already processed!");
        }

        order.setStatus(OrderStatus.REJECTED);
        Order updatedOrder = orderRepository.save(order);

        // 🔥 NOTIFICATION → Buyer
        notificationService.createNotification(
                order.getBuyer(),
                "Order Rejected",
                "Your order for " + order.getProduct().getName() + " was rejected"
        );

        return mapToResponse(updatedOrder);
    }

    // ===============================
    // OTHER METHODS (NO CHANGE)
    // ===============================

    public List<OrderResponse> getOrdersByFarmer(Long farmerId) {
        User farmer = userRepository.findById(farmerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Farmer not found"));

        List<Order> orders = orderRepository.findByFarmer(farmer);

        List<OrderResponse> list = new ArrayList<>();
        for (Order o : orders) {
            list.add(mapToResponse(o));
        }
        return list;
    }

    public List<OrderResponse> getOrdersByBuyer(Long buyerId) {
        User buyer = userRepository.findById(buyerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Buyer not found"));

        List<Order> orders = orderRepository.findByBuyer(buyer);

        List<OrderResponse> list = new ArrayList<>();
        for (Order o : orders) {
            list.add(mapToResponse(o));
        }
        return list;
    }

    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Order not found"));

        return mapToResponse(order);
    }

    private OrderResponse mapToResponse(Order order) {

        OrderResponse res = new OrderResponse();

        res.setId(order.getId());
        res.setQuantity(order.getQuantity());
        res.setMessage(order.getMessage());
        res.setStatus(order.getStatus());
        res.setCreatedAt(order.getCreatedAt());

        Product p = order.getProduct();
        res.setProductId(p.getId());
        res.setProductName(p.getName());
        res.setProductPrice(p.getPrice());

        if (order.getQuantity() != null && p.getPrice() != null) {
            res.setTotalPrice(order.getQuantity() * p.getPrice());
        }

        User b = order.getBuyer();
        res.setBuyerId(b.getId());
        res.setBuyerName(b.getName());
        res.setBuyerPhone(b.getPhone());

        User f = order.getFarmer();
        res.setFarmerId(f.getId());
        res.setFarmerName(f.getName());
        res.setFarmerPhone(f.getPhone());

        return res;
    }
}