package com.parking.lot.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.lot.dto.DependencyException;
import com.parking.lot.entity.ParkingSlot;
import com.parking.lot.entity.ParkingVehicle;
import com.parking.lot.entity.User;
import com.parking.lot.entity.Vehicle;
import com.parking.lot.repo.ParkingVehicleRepo;
import com.parking.lot.service.EmailService;
import com.parking.lot.service.ParkingSlotService;
import com.parking.lot.service.ParkingVehicleService;
import com.parking.lot.service.UserService;
import com.parking.lot.service.VehicleService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ParkingVehicleServiceImpl implements ParkingVehicleService {

	@Autowired
	ParkingVehicleRepo parkingVehicleRepo;

	@Autowired
	VehicleService vehicleService;

	@Autowired
	ParkingSlotService parkingSlotService;

	@Autowired
	UserService userService;

	@Autowired
	EmailService emailService;

	@Override
	public Optional<ParkingVehicle> findByStatusAndVehicle(String id) {
		Vehicle vehicle = vehicleService.findById(id).get();
		return parkingVehicleRepo.findByStatusAndVehicle(true, vehicle);
	}

	@Override
	public ParkingVehicle save(Vehicle vehicle) {
		if (null == vehicle) {
			throw new DependencyException("Vehicle Id is empty");
		}
		Optional<Vehicle> optionalVehicle = vehicleService.findById(vehicle.getId());
		if (optionalVehicle.isPresent()) {
			vehicle = optionalVehicle.get();
		} else {
			vehicle.setRegisteredOn(new Date());
			Optional<User> optionalUser = userService.findById(vehicle.getUser().getId());
			if (optionalUser.isPresent()) {
				vehicle.setUser(optionalUser.get());
			}
		}
		Optional<ParkingVehicle> optionalparkingVehicle = parkingVehicleRepo.findByStatusAndVehicle(true, vehicle);
		if (optionalparkingVehicle.isPresent()) {
			throw new DependencyException(vehicle.getId() + " is already Parked, Please find the Parking details "
					+ optionalparkingVehicle.get());
		}
		ParkingVehicle parkingVehicle = new ParkingVehicle();

		List<ParkingSlot> parkingSlots = parkingSlotService.findByActivityStatusAndParkingStatusAndType(true, false,
				vehicle.getType());
		if (parkingSlots.size() > 0) {
			parkingSlots.get(0).setParkingStatus(true);
			parkingVehicle.setVehicle(vehicle);
			parkingVehicle.setParkingSlot(parkingSlots.get(0));
			parkingVehicle.setStatus(true);
			parkingVehicle.setOccupiedAt(new Date());
		} else {
			throw new DependencyException("Parking Slot Not available for " + vehicle.getType() + " wheeler vehicle");
		}
		parkingVehicle = parkingVehicleRepo.save(parkingVehicle);
		String emailSendStatus = emailService.sendSimpleMail(parkingVehicle);
		log.info("emailSendStatus - " + emailSendStatus);
		return parkingVehicle;
	}

	@Override
	public ParkingVehicle save(String id) {
		Optional<ParkingVehicle> optionalparkingVehicle = this.findByStatusAndVehicle(id);
		if (!optionalparkingVehicle.isPresent()) {
			throw new DependencyException("Parking Slot Not Found for this vehicle " + id);
		}
		ParkingVehicle parkingVehicle = optionalparkingVehicle.get();
		parkingVehicle.setStatus(false);
		parkingVehicle.setReleasedAt(new Date());
		parkingVehicle.getParkingSlot().setParkingStatus(false);
		parkingVehicle = parkingVehicleRepo.save(parkingVehicle);
		String emailSendStatus = emailService.sendSimpleMail(parkingVehicle);
		log.info("emailSendStatus - " + emailSendStatus);
		return parkingVehicle;
	}

	@Override
	public List<ParkingVehicle> findByStatus() {
		return parkingVehicleRepo.findByStatus(true);
	}

	@Override
	public Optional<ParkingVehicle> findByStatusAndParkingSlot(boolean status, ParkingSlot parkingSlot) {
		return parkingVehicleRepo.findByStatusAndParkingSlot(status, parkingSlot);
	}

}
