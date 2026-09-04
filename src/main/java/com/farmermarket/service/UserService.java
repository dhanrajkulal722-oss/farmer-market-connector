package com.farmermarket.service;

import com.farmermarket.dto.request.RegisterRequest;
import com.farmermarket.dto.response.UserResponse;
import com.farmermarket.enums.UserStatus;
import com.farmermarket.exception.ResourceNotFoundException;
import com.farmermarket.model.User;
import com.farmermarket.repository.UserRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * ══════════════════════════════════════
 * USER SERVICE
 * ══════════════════════════════════════
 *
 * Contains business logic for user operations.
 *
 * Rules handled here:
 * → Email must be unique
 * → Phone must be unique
 * → Password encoding
 * → Building user object
 * → Mapping to response DTO
 * ══════════════════════════════════════
 */
@Service
public class UserService {

    // ══════════════════════════════════════
    // DEPENDENCIES
    // ══════════════════════════════════════

    private final UserRepository userRepository;

    /*
     * PasswordEncoder is provided by
     * SecurityConfig.
     *
     * It uses BCrypt to encode passwords.
     */
    private final PasswordEncoder passwordEncoder;


    // ══════════════════════════════════════
    // CONSTRUCTOR
    // ══════════════════════════════════════

    public UserService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    // ══════════════════════════════════════
    // REGISTER USER
    // ══════════════════════════════════════

    /**
     * Register a new user.
     *
     * Steps:
     * 1. Check email exists
     * 2. Check phone exists
     * 3. Build User object
     * 4. Encode password
     * 5. Save to database
     * 6. Return safe response
     */
    public UserResponse register(RegisterRequest request) {

        // ══════════════════════════════════
        // STEP 1: CHECK EMAIL
        // ══════════════════════════════════

        if (userRepository.existsByEmail(request.getEmail())) {

            throw new RuntimeException(
                    "Email already registered! " +
                    "Please use different email.");
        }


        // ══════════════════════════════════
        // STEP 2: CHECK PHONE
        // ══════════════════════════════════

        if (userRepository.existsByPhone(request.getPhone())) {

            throw new RuntimeException(
                    "Phone number already registered! " +
                    "Please use different number.");
        }


        // ══════════════════════════════════
        // STEP 3: BUILD USER
        // ══════════════════════════════════

        User user = new User();

        user.setName(request.getName());

        user.setEmail(request.getEmail());


        // ══════════════════════════════════
        // STEP 4: ENCODE PASSWORD
        // ══════════════════════════════════
        //
        // IMPORTANT:
        //
        // We NEVER store the plain password.
        //
        // Example:
        //
        // password123
        //      ↓
        // BCrypt
        //      ↓
        // $2a$10$................
        //
        // Only the BCrypt hash is stored
        // in the database.
        //
        user.setPassword(
                passwordEncoder.encode(
                        request.getPassword()
                )
        );


        user.setPhone(request.getPhone());

        user.setRole(request.getRole());

        user.setLocation(request.getLocation());

        user.setStatus(UserStatus.ACTIVE);

        user.setIsVerified(false);

        user.setIsActive(true);


        // ══════════════════════════════════
        // STEP 5: SAVE TO DATABASE
        // ══════════════════════════════════

        User savedUser =
                userRepository.save(user);


        // ══════════════════════════════════
        // STEP 6: RETURN SAFE RESPONSE
        // ══════════════════════════════════
        //
        // UserResponse does NOT contain
        // password.
        //

        return mapToResponse(savedUser);
    }


    // ══════════════════════════════════════
    // GET USER BY ID
    // ══════════════════════════════════════

    public UserResponse getUserById(Long id) {

        User user = userRepository
                .findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User",
                                "id",
                                id));

        return mapToResponse(user);
    }


    // ══════════════════════════════════════
    // GET ALL USERS
    // ══════════════════════════════════════

    public List<UserResponse> getAllUsers() {

        List<User> users =
                userRepository.findAll();

        List<UserResponse> responseList =
                new ArrayList<>();

        for (User user : users) {

            responseList.add(
                    mapToResponse(user)
            );
        }

        return responseList;
    }


    // ══════════════════════════════════════
    // MAP USER → RESPONSE DTO
    // ══════════════════════════════════════

    /**
     * Converts User entity into
     * UserResponse DTO.
     *
     * IMPORTANT:
     * Password is intentionally NOT copied.
     */
    private UserResponse mapToResponse(User user) {

        UserResponse response =
                new UserResponse();

        response.setId(user.getId());

        response.setName(user.getName());

        response.setEmail(user.getEmail());

        // Password is NOT returned.

        response.setPhone(user.getPhone());

        response.setRole(user.getRole());

        response.setStatus(user.getStatus());

        response.setLocation(user.getLocation());

        response.setCreatedAt(user.getCreatedAt());

        return response;
    }
}