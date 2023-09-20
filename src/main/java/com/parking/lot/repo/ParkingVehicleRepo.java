package com.parking.lot.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.parking.lot.entity.ParkingSlot;
import com.parking.lot.entity.ParkingVehicle;
import com.parking.lot.entity.Vehicle;

@Repository
public interface ParkingVehicleRepo
		extends JpaRepository<ParkingVehicle, Long>, JpaSpecificationExecutor<ParkingVehicle> {
	Optional<ParkingVehicle> findByStatusAndVehicle(boolean status, Vehicle vehicle);

	List<ParkingVehicle> findByStatus(boolean status);

	Optional<ParkingVehicle> findByStatusAndParkingSlot(boolean status, ParkingSlot parkingSlot);
}
