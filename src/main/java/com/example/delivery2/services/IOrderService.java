package com.example.delivery2.services;

import com.example.delivery2.dto.ResponseDto;
import com.example.delivery2.models.Order;

import java.util.List;
import java.util.UUID;

public interface IOrderService {
    List<Order> getAllOrders();

    Order getOrderById(UUID id);

    Order createOrder(Order order);

    Order updateOrder(UUID id, Order updatedOrder);

    ResponseDto makeDiscount(UUID id, double discount);
    boolean deleteOrder(UUID id);
}
