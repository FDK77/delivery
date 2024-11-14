package com.example.delivery2.services.impl;

import com.example.delivery2.dto.ResponseDto;
import com.example.delivery2.models.Order;
import com.example.delivery2.models.Status;
import com.example.delivery2.repositories.OrderRepository;

import com.example.delivery2.services.IOrderService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class OrderServiceImpl implements IOrderService {

    private OrderRepository orderRepository;

    private RabbitSendService rabbitSendService;
    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public void setRabbitSendService(RabbitSendService rabbitSendService) {this.rabbitSendService = rabbitSendService;}

    @Autowired
    public void setOrderRepository(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    @Autowired
    public OrderServiceImpl(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    @Override
    public Order getOrderById(UUID id) {
        return orderRepository.findById(id).orElse(null);
    }

    @Override
    public Order createOrder(Order order) {
        order.setStatus(Status.ASSEMBLING);
        Order saveOrder = orderRepository.save(order);
        rabbitSendService.sendOrderCreate(order.getId(), order.getStatus().toString());
        return saveOrder;
    }

    @Override
    public Order updateOrder(UUID id, Order updatedOrder) {
        return orderRepository.findById(id).map(order -> {
            String oldStatus = order.getStatus().toString();
            order.setOrderDate(updatedOrder.getOrderDate());
            order.setCustomerName(updatedOrder.getCustomerName());
            order.setCustomerAddress(updatedOrder.getCustomerAddress());
            order.setOrderCost(updatedOrder.getOrderCost());
            order.setDeliveryInstructions(updatedOrder.getDeliveryInstructions());
            order.setDeliveryDate(updatedOrder.getDeliveryDate());
            order.setStatus(updatedOrder.getStatus());
            order.setDeliverer(updatedOrder.getDeliverer());
            orderRepository.save(order);
            String newStatus = order.getStatus().toString();
            if (oldStatus != newStatus){
               rabbitSendService.sendOrderStatusUpdate(id,newStatus);
            }
            return order;
        }).orElse(null);
    }

    @Override
    public ResponseDto makeDiscount(UUID id, double discount) {
        ResponseDto response = null;
        Optional<Order> optionalOrder = orderRepository.findById(id);
        if (optionalOrder.isPresent()) {
            Order order = optionalOrder.get();
            double oldPrice = order.getOrderCost();
            double newPrice = oldPrice - (oldPrice * discount);
            order.setOrderCost(newPrice);
            orderRepository.save(order);
            System.out.println("Стоимость заказа с ID: " + id + " поменялась с " + oldPrice + " на " + newPrice + ".");
            response = new ResponseDto(id, oldPrice, newPrice);
        } else {
            System.out.println("Заказ с ID: " + id + " не найден.");
        }
        return response;
    }



    @Override
    public boolean deleteOrder(UUID id) {
        if (orderRepository.existsById(id)){
            orderRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
