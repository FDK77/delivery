package com.example.delivery2.dto;

import java.util.UUID;

public class ResponseDto {
    UUID orderId;
    double oldPrice;
    double newPrice;

    public ResponseDto(UUID orderId, double oldPrice, double newPrice) {
        this.orderId = orderId;
        this.oldPrice = oldPrice;
        this.newPrice = newPrice;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public void setOrderId(UUID orderId) {
        this.orderId = orderId;
    }

    public double getOldPrice() {
        return oldPrice;
    }

    public void setOldPrice(double oldPrice) {
        this.oldPrice = oldPrice;
    }

    public double getNewPrice() {
        return newPrice;
    }

    public void setNewPrice(double newPrice) {
        this.newPrice = newPrice;
    }
}
