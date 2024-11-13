package com.example.delivery2.services.impl;

import com.example.delivery2.models.Deliverer;

import com.example.delivery2.repositories.DelivererRepository;
import com.example.delivery2.services.IDelivererService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DelivererServiceImpl implements IDelivererService {
    private DelivererRepository delivererRepository;

    @Autowired
    public void setDelivererRepository(DelivererRepository delivererRepository) {
        this.delivererRepository = delivererRepository;
    }

    @Override
    public List<Deliverer> getAllDeliverers() {
        return delivererRepository.findAll();
    }
    @Override
    public Deliverer getDelivererById(UUID id) {
        return delivererRepository.findById(id).orElse(null);
    }
    @Override
    public Deliverer createDeliverer(Deliverer deliverer) {
        return delivererRepository.save(deliverer);
    }
    @Override
    public Deliverer updateDeliverer(UUID id, Deliverer updatedDeliverer) {
        return delivererRepository.findById(id).map(deliverer -> {
            deliverer.setName(updatedDeliverer.getName());
            deliverer.setPhoneNumber(updatedDeliverer.getPhoneNumber());
            deliverer.setEmail(updatedDeliverer.getEmail());
            deliverer.setVehicleType(updatedDeliverer.getVehicleType());
            deliverer.setRating(updatedDeliverer.getRating());
            return delivererRepository.save(deliverer);
        }).orElse(null);
    }

    @Override
    public boolean deleteDeliverer(UUID id) {
        if (delivererRepository.existsById(id)) {
            delivererRepository.deleteById(id);
            return true;
        }
        return false;
    }

}
