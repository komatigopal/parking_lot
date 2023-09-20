package com.parking.lot.service;

import com.parking.lot.dto.EmailDetails;
import com.parking.lot.entity.ParkingSlot;
import com.parking.lot.entity.ParkingVehicle;
import com.parking.lot.entity.Vehicle;

public interface EmailService {
	String sendSimpleMail(EmailDetails details);

	String sendMailWithAttachment(EmailDetails details);

	String sendSimpleMail(ParkingVehicle parkingVehicle);

	String sendSimpleMail(ParkingSlot parkingSlot);

	String sendSimpleMail(Vehicle vehicle);
}
