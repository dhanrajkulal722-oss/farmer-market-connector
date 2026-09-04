package com.farmermarket.controller;

import com.farmermarket.dto.request.RegisterRequest;
import com.farmermarket.dto.response.UserResponse;
import com.farmermarket.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // ══════════════════════════════
    // API 1 — REGISTER USER
    // POST /api/users/register
    // ══════════════════════════════
    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@RequestBody @Valid RegisterRequest request) {

        UserResponse userResponse =userService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    // ══════════════════════════════
    // API 2 — GET USER BY ID
    // GET /api/users/1
    // ══════════════════════════════
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable Long id) {

        UserResponse userResponse =userService.getUserById(id);

        return ResponseEntity.ok(userResponse);
    }

    // ══════════════════════════════
    // API 3 — GET ALL USERS
    // GET /api/users
    // ══════════════════════════════
    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {

        List<UserResponse> users =userService.getAllUsers();

        return ResponseEntity.ok(users);
    }
}