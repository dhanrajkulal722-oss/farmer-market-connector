package com.farmermarket.repository;

import com.farmermarket.model.User;
import com.farmermarket.enums.Role;
import org.springframework.data.jpa.repository
        .JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

/**
 * ══════════════════════════════════════
 * USER REPOSITORY
 * ══════════════════════════════════════
 *
 * This interface talks to database!
 *
 * We extend JpaRepository which
 * gives us FREE methods:
 *
 * save(user)
 * → INSERT or UPDATE in database
 *
 * findById(id)
 * → SELECT WHERE id = ?
 *
 * findAll()
 * → SELECT all users
 *
 * deleteById(id)
 * → DELETE WHERE id = ?
 *
 * existsById(id)
 * → Check if exists
 *
 * count()
 * → COUNT all users
 *
 * We write ZERO implementation!
 * Spring generates SQL automatically!
 * ══════════════════════════════════════
 */
@Repository
public interface UserRepository
        extends JpaRepository<User, Long> {

    /**
     * Find user by email
     *
     * Spring reads method name:
     * findBy = SELECT WHERE
     * Email  = email column
     *
     * Auto generates SQL:
     * SELECT * FROM users
     * WHERE email = ?
     *
     * Returns Optional<User>
     * Because user might not exist!
     *
     * Usage in service:
     * userRepository
     *     .findByEmail("r@gmail.com")
     *     .orElseThrow(() ->
     *         new ResourceNotFoundException(
     *             "User", "email",
     *             "r@gmail.com"));
     */
    Optional<User> findByEmail(
            String email);

    /**
     * Find user by phone
     *
     * Auto generates SQL:
     * SELECT * FROM users
     * WHERE phone = ?
     */
    Optional<User> findByPhone(
            String phone);

    /**
     * Check if email exists
     *
     * Auto generates SQL:
     * SELECT COUNT(*) > 0
     * FROM users
     * WHERE email = ?
     *
     * Returns true or false!
     *
     * Usage in service:
     * if(userRepository
     *         .existsByEmail(email)) {
     *     throw new RuntimeException(
     *         "Email already exists!");
     * }
     */
    Boolean existsByEmail(String email);

    /**
     * Check if phone exists
     *
     * Auto generates SQL:
     * SELECT COUNT(*) > 0
     * FROM users
     * WHERE phone = ?
     */
    Boolean existsByPhone(String phone);

    /**
     * Find all users by role
     *
     * Auto generates SQL:
     * SELECT * FROM users
     * WHERE role = ?
     *
     * Usage:
     * userRepository
     *     .findByRole(Role.FARMER)
     * Returns all farmers!
     */
    List<User> findByRole(Role role);
}