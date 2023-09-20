package com.parking.lot.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.lot.entity.User;
import com.parking.lot.entity.Vehicle;
import com.parking.lot.repo.VehicleRepo;
import com.parking.lot.service.EmailService;
import com.parking.lot.service.UserService;
import com.parking.lot.service.VehicleService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class VechicleServiceImpl implements VehicleService {

	@Autowired
	private VehicleRepo vehicleRepo;

	@Autowired
	EmailService emailService;

	@Autowired
	UserService userService;

	@Override
	public Optional<Vehicle> findById(String id) {
		return vehicleRepo.findById(id);
	}

	@Override
	public Vehicle save(Vehicle vehicle) {
		Optional<User> optionalUser = userService.findById(vehicle.getUser().getId());
		if (optionalUser.isPresent()) {
			vehicle.setUser(optionalUser.get());
		}
		vehicle.setRegisteredOn(new Date());
		vehicle = vehicleRepo.save(vehicle);
		String emailSendStatus = emailService.sendSimpleMail(vehicle);
		log.info("emailSendStatus - " + emailSendStatus);
		return vehicle;
	}

	@Override
	public List<Vehicle> findAll() {
		return vehicleRepo.findAll();
	}

}
