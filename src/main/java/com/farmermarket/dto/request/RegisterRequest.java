package com.farmermarket.dto.request;

import com.farmermarket.enums.Role;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterRequest {

    // ════════════════════════════
    // FIELDS WITH VALIDATION
    // ════════════════════════════

    @NotBlank(message = "Name is required")
    @Size(
        min = 2,
        max = 100,
        message = "Name must be 2 to 100 characters")
    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Please enter valid email")
    private String email;

    @NotBlank(message = "Password is required")
    @Size(
        min = 8,
        max = 20,
        message = "Password must be 8 to 20 characters")
    private String password;

    @NotBlank(
        message = "Phone is required")
    @Pattern(
        regexp = "^[0-9]{10}$",
        message = "Phone must be 10 digits")
    private String phone;

    @NotNull( message = "Role is required")
    private Role role;

    private String location;

    // ════════════════════════════
    // EMPTY CONSTRUCTOR
    // Jackson needs this!
    // ════════════════════════════
    public RegisterRequest() {
    }

    // ════════════════════════════
    // GETTERS
    // Used to READ values
    // FROM this object!
    //
    // Service uses these:
    // request.getName()
    // request.getEmail()
    // request.getPhone()
    // ════════════════════════════

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public Role getRole() {
        return role;
    }

    public String getLocation() {
        return location;
    }

    // ════════════════════════════
    // SETTERS
    // Used to WRITE values
    // INTO this object!
    //
    // Jackson uses these:
    // request.setName("Ramesh")
    // request.setEmail("r@g.com")
    // ════════════════════════════

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(
            String password) {
        this.password = password;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public void setLocation(
            String location) {
        this.location = location;
    }
}