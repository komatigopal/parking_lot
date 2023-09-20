package com.parking.lot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.parking.lot.entity.Vehicle;
import com.parking.lot.service.ParkingVehicleService;
import com.parking.lot.service.VehicleService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/vehicle")
public class VehicleController {

	@Autowired
	ParkingVehicleService parkingVehicleService;

	@Autowired
	VehicleService vehicleService;

	@GetMapping("")
	public List<Vehicle> getAllVehicles() {
		log.info("coming to getAllVehicles");
		return vehicleService.findAll();
	}

	@GetMapping("/{id}")
	public Vehicle getVehicle(@PathVariable String id) {
		log.info("coming to getVehicle  id - " + id);
		return vehicleService.findById(id).get();
	}

	@PostMapping("")
	public Vehicle saveVehicle(@RequestBody Vehicle vehicle) {
		log.info("coming to saveVehicle  vehicle - " + vehicle);
		return vehicleService.save(vehicle);
	}

}
