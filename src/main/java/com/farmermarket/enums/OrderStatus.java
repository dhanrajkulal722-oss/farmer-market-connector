package com.farmermarket.enums;

public enum OrderStatus {

    PENDING,    // Buyer showed interest
                // Waiting for farmer

    ACCEPTED,   // Farmer accepted
                // Both connected via WhatsApp

    REJECTED,   // Farmer rejected
                // Buyer needs to find another

    COMPLETED   // Deal done successfully
}
