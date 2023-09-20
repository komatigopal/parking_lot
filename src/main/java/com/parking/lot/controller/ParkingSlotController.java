package com.parking.lot.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.parking.lot.entity.ParkingSlot;
import com.parking.lot.service.ParkingSlotService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/parking")
public class ParkingSlotController {

	@Autowired
	ParkingSlotService parkingSlotService;

	@GetMapping("")
	public List<ParkingSlot> getAllParkingSlots(@RequestParam boolean activityStatus) {
		log.info("coming to getAllParkingSlots");
		return parkingSlotService.findByActivityStatus(activityStatus);
	}

	@GetMapping("/{id}")
	public ParkingSlot getParkingSlot(@PathVariable Long id) {
		log.info("coming to getParkingSlot  id - " + id);
		return parkingSlotService.findById(id).get();
	}

	@PostMapping("")
	public ParkingSlot saveParkingSlot(@RequestBody ParkingSlot parkingSlot) {
		log.info("coming to saveParkingSlot  parkingSlot - " + parkingSlot);
		return parkingSlotService.save(parkingSlot);
	}

	@PutMapping("/{id}")
	public ParkingSlot updateParkingSlot(@PathVariable long id, @RequestParam boolean activityStatus) {
		log.info("coming to updateParkingSlot id - " + id + ", activityStatus - " + activityStatus);
		return parkingSlotService.save(id, activityStatus);
	}
}
