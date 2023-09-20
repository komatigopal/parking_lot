package com.parking.lot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.lot.entity.ParkingVehicle;
import com.parking.lot.entity.Vehicle;
import com.parking.lot.service.ParkingVehicleService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/vehicle/parking")
public class ParkingVehicleController {
	@Autowired
	ParkingVehicleService parkingVehicleService;

	@GetMapping("")
	public List<ParkingVehicle> getAllParkingVehicles() {
		log.info("coming to getAllParkingVehicles");
		return parkingVehicleService.findByStatus();
	}

	@GetMapping("/{vehicleId}")
	public ParkingVehicle getParkingVehicle(@PathVariable String vehicleId) {
		log.info("coming to getParkingVehicle id - " + vehicleId);
		return parkingVehicleService.findByStatusAndVehicle(vehicleId).get();
	}

	@PostMapping("")
	public ParkingVehicle saveParkingVehicle(@RequestBody Vehicle vehicle) {
		log.info("coming to saveParkingVehicle vehicle - " + vehicle);
		return parkingVehicleService.save(vehicle);
	}

	@PutMapping("/{vehicleId}")
	public ParkingVehicle updateParkingVehicle(@PathVariable String vehicleId) {
		log.info("coming to updateParkingVehicle id - " + vehicleId);
		return parkingVehicleService.save(vehicleId);
	}

}
