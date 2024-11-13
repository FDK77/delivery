package com.example.delivery2.services;

import com.example.delivery2.models.Deliverer;

import java.util.List;
import java.util.UUID;

public interface IDelivererService {

    Deliverer updateDeliverer(UUID id, Deliverer updatedDeliverer);

    List<Deliverer> getAllDeliverers();

    Deliverer getDelivererById(UUID id);

    Deliverer createDeliverer(Deliverer deliverer);

    boolean deleteDeliverer(UUID id);
}
