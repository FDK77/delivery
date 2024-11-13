package com.example.delivery2;


import com.example.delivery2.models.Deliverer;
import com.example.delivery2.models.Order;
import com.example.delivery2.models.Status;
import com.example.delivery2.repositories.DelivererRepository;
import com.example.delivery2.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class DataInitializer implements CommandLineRunner {

    @Autowired
    private DelivererRepository delivererRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public void run(String... args) throws Exception {
        // Создание доставщиков
        Deliverer deliverer1 = new Deliverer();
        deliverer1.setName("Иван Иванов");
        deliverer1.setPhoneNumber("+79001234567");
        deliverer1.setEmail("ivan@example.com");
        deliverer1.setVehicleType("Велосипед");
        deliverer1.setRating(4.5);

        Deliverer deliverer2 = new Deliverer();
        deliverer2.setName("Петр Петров");
        deliverer2.setPhoneNumber("+79009876543");
        deliverer2.setEmail("petr@example.com");
        deliverer2.setVehicleType("Машина");
        deliverer2.setRating(4.7);

        delivererRepository.save(deliverer1);
        delivererRepository.save(deliverer2);


        Order order1 = new Order();
        order1.setOrderDate(LocalDateTime.now());
        order1.setCustomerName("Алексей Сидоров");
        order1.setCustomerAddress("Москва, ул. Ленина, д. 1");
        order1.setOrderCost(1200);
        order1.setDeliveryInstructions("Оставить у двери");
        order1.setDeliveryDate(LocalDateTime.now().plusDays(1));
        order1.setStatus(Status.IN_TRANSIT);
        order1.setDeliverer(deliverer1);

        Order order2 = new Order();
        order2.setOrderDate(LocalDateTime.now());
        order2.setCustomerName("Мария Кузнецова");
        order2.setCustomerAddress("Москва, ул. Пушкина, д. 2");
        order2.setOrderCost(1500);
        order2.setDeliveryInstructions("Позвонить перед доставкой");
        order2.setDeliveryDate(LocalDateTime.now().plusDays(2));
        order2.setStatus(Status.ARRIVED);
        order2.setDeliverer(deliverer2);

        orderRepository.save(order1);
        orderRepository.save(order2);
    }
}

