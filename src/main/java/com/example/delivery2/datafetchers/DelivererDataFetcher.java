package com.example.delivery2.datafetchers;

import com.example.delivery2.models.Deliverer;
import com.example.delivery2.services.impl.DelivererServiceImpl;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@DgsComponent
public class DelivererDataFetcher {
    private DelivererServiceImpl delivererService;

    @Autowired
    public void setDelivererService(DelivererServiceImpl delivererService) {
        this.delivererService = delivererService;
    }

    @DgsQuery
    public List<Deliverer> deliverers(@InputArgument String nameFilter) {
        List<Deliverer> deliverers = delivererService.getAllDeliverers();
        if (nameFilter == null || nameFilter.isEmpty()) {
            return deliverers;
        }
        return deliverers.stream()
                .filter(deliverer -> deliverer.getName().toLowerCase().contains(nameFilter.toLowerCase()))
                .collect(Collectors.toList());
    }

    @DgsMutation
    public Deliverer addDeliverer(@InputArgument InputDeliverer deliverer) {
        Deliverer newDeliverer = new Deliverer();
        newDeliverer.setName(deliverer.name());
        newDeliverer.setPhoneNumber(deliverer.phoneNumber());
        newDeliverer.setEmail(deliverer.email());
        newDeliverer.setVehicleType(deliverer.vehicleType());
        newDeliverer.setRating(deliverer.rating());

        return delivererService.createDeliverer(newDeliverer);
    }

    @DgsMutation
    public Deliverer updateDeliverer(@InputArgument String id, @InputArgument("deliverer") InputDeliverer delivererInput) {
        UUID uuid = UUID.fromString(id);
        Deliverer existingDeliverer = delivererService.getDelivererById(uuid);

        if (existingDeliverer == null) {
            throw new RuntimeException("Deliverer not found");
        }
        existingDeliverer.setName(delivererInput.name());
        existingDeliverer.setPhoneNumber(delivererInput.phoneNumber());
        existingDeliverer.setEmail(delivererInput.email());
        existingDeliverer.setVehicleType(delivererInput.vehicleType());
        existingDeliverer.setRating(delivererInput.rating());

        return delivererService.updateDeliverer(uuid,existingDeliverer);
    }

    @DgsMutation
    public boolean deleteDeliverer(@InputArgument("id") String id) {
        return delivererService.deleteDeliverer(UUID.fromString(id));
    }
}
