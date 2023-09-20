package com.parking.lot.service;

import java.util.List;
import java.util.Optional;

import com.parking.lot.entity.ParkingSlot;
import com.parking.lot.entity.ParkingVehicle;
import com.parking.lot.entity.Vehicle;

public interface ParkingVehicleService {
	Optional<ParkingVehicle> findByStatusAndVehicle(String id);

	ParkingVehicle save(Vehicle vehicle);

	ParkingVehicle save(String id);

	List<ParkingVehicle> findByStatus();

	Optional<ParkingVehicle> findByStatusAndParkingSlot(boolean status, ParkingSlot parkingSlot);
}
