package com.parking.lot.service;

import java.util.List;
import java.util.Optional;

import com.parking.lot.entity.Vehicle;

public interface VehicleService {
	Optional<Vehicle> findById(String id);

	Vehicle save(Vehicle vehicle);

	List<Vehicle> findAll();
}
