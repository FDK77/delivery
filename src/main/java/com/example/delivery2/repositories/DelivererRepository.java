package com.example.delivery2.repositories;

import com.example.delivery2.models.Deliverer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface DelivererRepository extends JpaRepository<Deliverer, UUID> {
}
