package com.parking.lot.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.parking.lot.dto.DependencyException;
import com.parking.lot.entity.ParkingSlot;
import com.parking.lot.entity.ParkingVehicle;
import com.parking.lot.repo.ParkingSlotRepo;
import com.parking.lot.service.EmailService;
import com.parking.lot.service.ParkingSlotService;
import com.parking.lot.service.ParkingVehicleService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ParkingSlotServiceImpl implements ParkingSlotService {
	@Autowired
	ParkingSlotRepo parkingSlotRepo;

	@Autowired
	EmailService emailService;

	@Autowired
	ParkingVehicleService parkingVehicleService;

	@Override
	public Optional<ParkingSlot> findById(Long id) {
		return parkingSlotRepo.findById(id);
	}

	@Override
	public ParkingSlot save(ParkingSlot parkingSlot) {
		parkingSlot.setCreatedAt(new Date());
		parkingSlot.setParkingStatus(false);
		parkingSlot.setActivityStatus(true);
		parkingSlot.getLocation().setNavigation(parkingSlot.getLocation().settingNavigation());
		parkingSlot = parkingSlotRepo.save(parkingSlot);
		String emailSendStatus = emailService.sendSimpleMail(parkingSlot);
		log.info("emailSendStatus - " + emailSendStatus);
		return parkingSlot;
	}

	@Override
	public List<ParkingSlot> findByParkingStatusAndType(boolean status, String type) {
		return parkingSlotRepo.findByParkingStatusAndType(status, type);
	}

	@Override
	public List<ParkingSlot> findByActivityStatusAndParkingStatusAndType(boolean activityStatus, boolean status,
			String type) {
		return parkingSlotRepo.findByActivityStatusAndParkingStatusAndType(activityStatus, status, type);
	}

	@Override
	public ParkingSlot save(long id, boolean activityStatus) {
		Optional<ParkingSlot> optionalparkingSlot = findById(id);
		if (!optionalparkingSlot.isPresent()) {
			throw new DependencyException("Parking Slot Not Exist with id - " + id);
		}
		ParkingSlot parkingSlot = optionalparkingSlot.get();
		if (parkingSlot.isActivityStatus() == activityStatus) {
			throw new DependencyException("Parking Slot with id - " + id + " already in activity status "
					+ activityStatus + ", Please find the parking slot details " + parkingSlot);
		}
		if (parkingSlot.isParkingStatus() && !activityStatus) {
			ParkingVehicle parkingVehicle = parkingVehicleService.findByStatusAndParkingSlot(true, parkingSlot).get();
			throw new DependencyException(
					"Parking Slot with id - " + id + " alredy occupied, Please find the details " + parkingVehicle);
		}
		parkingSlot.setActivityStatus(activityStatus);
		parkingSlot = parkingSlotRepo.save(parkingSlot);
		String emailSendStatus = emailService.sendSimpleMail(parkingSlot);
		log.info("emailSendStatus - " + emailSendStatus);
		return parkingSlot;
	}

	@Override
	public List<ParkingSlot> findByActivityStatus(boolean activityStatus) {
		return parkingSlotRepo.findByActivityStatus(activityStatus);
	}

}
