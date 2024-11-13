package com.example.delivery2.datafetchers;

import com.example.delivery2.models.Status;

public record InputOrder(String orderDate,
                         String customerName, String customerAddress,
                         Float orderCost, String deliveryInstructions,
                         String deliveryDate, Status status,
                         String delivererId) {}
