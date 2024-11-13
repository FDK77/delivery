package com.example.delivery2.services.impl;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.example.delivery2.dto.OrderStatusUpdateMessage;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RabbitSendService {
    private final RabbitTemplate rabbitTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();
    static final String exchangeName = "testExchange";

    @Autowired
    public RabbitSendService(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    public void sendOrderStatusUpdate(UUID orderId, String currentStatus) {
        OrderStatusUpdateMessage message = new OrderStatusUpdateMessage(orderId, currentStatus, LocalDateTime.now());
        rabbitTemplate.convertAndSend(exchangeName, "my.key", message);
    }


    public void sendOrderCreate(UUID orderId, String currentStatus) {
        OrderStatusUpdateMessage message = new OrderStatusUpdateMessage(orderId, currentStatus, LocalDateTime.now());
        try {
            String jsonMessage = objectMapper.writeValueAsString(message);
            rabbitTemplate.convertAndSend(exchangeName, "my.key", jsonMessage);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

}

