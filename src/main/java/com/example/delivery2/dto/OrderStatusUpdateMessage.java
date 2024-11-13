package com.example.delivery2.dto;

import java.time.LocalDateTime;
import java.util.UUID;

public class OrderStatusUpdateMessage {
    private UUID orderId;
    private String status;

    private LocalDateTime date;

    public OrderStatusUpdateMessage(UUID orderId, String status, LocalDateTime date) {
        this.orderId = orderId;
        this.status = status;
        this.date = date;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
