package com.example.delivery2.controllers;

import com.example.delivery2.models.Deliverer;
import com.example.delivery2.services.impl.DelivererServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/deliverers")
public class DelivererController {

    private DelivererServiceImpl delivererService;

    @Autowired
    public void setDelivererService(DelivererServiceImpl delivererService) {
        this.delivererService = delivererService;
    }

    @GetMapping
    public List<Deliverer> getAllDeliverers() {
        return delivererService.getAllDeliverers();
    }

    @GetMapping("/{id}")
    public ResponseEntity<EntityModel<Deliverer>> getDeliverer(@PathVariable UUID id) {
        Deliverer deliverer = delivererService.getDelivererById(id);
        if (deliverer == null) {
            return ResponseEntity.notFound().build();
        }
        EntityModel<Deliverer> resource = EntityModel.of(deliverer);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DelivererController.class).getAllDeliverers()).withRel("all-deliverers"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DelivererController.class).deleteDeliverer(id)).withRel("delete"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DelivererController.class).updateDeliverer(id,deliverer)).withRel("update"));
        return ResponseEntity.ok(resource);
    }

    @PostMapping
    public Deliverer createDeliverer(@RequestBody Deliverer deliverer) {
        return delivererService.createDeliverer(deliverer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EntityModel<Deliverer>> updateDeliverer(@PathVariable UUID id, @RequestBody Deliverer deliverer) {
        Deliverer updatedDeliverer = delivererService.updateDeliverer(id, deliverer);
        if (updatedDeliverer == null) {
            return ResponseEntity.notFound().build();
        }
        EntityModel<Deliverer> resource = EntityModel.of(updatedDeliverer);
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DelivererController.class).getDeliverer(id)).withSelfRel());
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DelivererController.class).getAllDeliverers()).withRel("all-deliverers"));
        resource.add(WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(DelivererController.class).deleteDeliverer(id)).withRel("delete"));
        return ResponseEntity.ok(resource);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDeliverer(@PathVariable UUID id) {
        Deliverer deliverer = delivererService.getDelivererById(id);
        if (deliverer == null) {
            return ResponseEntity.notFound().build();
        }
        delivererService.deleteDeliverer(id);
        return ResponseEntity.noContent().build();
    }

}