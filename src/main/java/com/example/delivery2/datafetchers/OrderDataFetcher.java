package com.example.delivery2.datafetchers;

import com.example.delivery2.models.Deliverer;
import com.example.delivery2.models.Order;
import com.example.delivery2.services.impl.DelivererServiceImpl;
import com.example.delivery2.services.impl.OrderServiceImpl;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DgsComponent
public class OrderDataFetcher {
    private OrderServiceImpl orderService;

    private DelivererServiceImpl delivererService;

    @Autowired
    public void setOrderService(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setDelivererService(DelivererServiceImpl delivererService) {
        this.delivererService = delivererService;
    }

    @DgsQuery
    public List<Order> orders(@InputArgument String customerNameFilter) {
        List<Order> orders = orderService.getAllOrders();
        if (customerNameFilter == null || customerNameFilter.isEmpty()) {
            return orders;
        }
        return orders.stream()
                .filter(order -> order.getCustomerName().toLowerCase().contains(customerNameFilter.toLowerCase()))
                .collect(Collectors.toList());
    }

    @DgsMutation
    public Order addOrder(@InputArgument("order") InputOrder orderInput) {
        Order newOrder = new Order();
        newOrder.setOrderDate(LocalDateTime.parse(orderInput.orderDate()));
        newOrder.setCustomerName(orderInput.customerName());
        newOrder.setCustomerAddress(orderInput.customerAddress());
        newOrder.setOrderCost(orderInput.orderCost());
        newOrder.setDeliveryInstructions(orderInput.deliveryInstructions());
        newOrder.setDeliveryDate(LocalDateTime.parse(orderInput.deliveryDate()));
        newOrder.setStatus(orderInput.status());

        Deliverer deliverer = delivererService.getDelivererById(UUID.fromString(orderInput.delivererId()));
        newOrder.setDeliverer(deliverer);

        return orderService.createOrder(newOrder);
    }

    @DgsMutation
    public Order updateOrder(@InputArgument String id, @InputArgument("order") InputOrder orderInput) {
        UUID uuid = UUID.fromString(id);
        Order existingOrder = orderService.getOrderById(uuid);

        if (existingOrder == null) {
            throw new RuntimeException("Order not found");
        }
        existingOrder.setOrderDate(LocalDateTime.parse(orderInput.orderDate()));
        existingOrder.setCustomerName(orderInput.customerName());
        existingOrder.setCustomerAddress(orderInput.customerAddress());
        existingOrder.setOrderCost(orderInput.orderCost());
        existingOrder.setDeliveryInstructions(orderInput.deliveryInstructions());
        existingOrder.setDeliveryDate(LocalDateTime.parse(orderInput.deliveryDate()));
        existingOrder.setStatus(orderInput.status());


        Deliverer deliverer = delivererService.getDelivererById(UUID.fromString(orderInput.delivererId()));
        existingOrder.setDeliverer(deliverer);

        return orderService.updateOrder(uuid, existingOrder);
    }

    @DgsMutation
    public boolean deleteOrder(@InputArgument("id") String id) {
        return orderService.deleteOrder(UUID.fromString(id));
    }
}
