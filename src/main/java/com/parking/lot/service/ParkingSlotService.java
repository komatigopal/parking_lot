package com.parking.lot.service;

import java.util.List;
import java.util.Optional;

import com.parking.lot.entity.ParkingSlot;

public interface ParkingSlotService {
	public Optional<ParkingSlot> findById(Long id);

	ParkingSlot save(ParkingSlot parkingSlot);

	List<ParkingSlot> findByParkingStatusAndType(boolean status, String type);

	public List<ParkingSlot> findByActivityStatusAndParkingStatusAndType(boolean activityStatus, boolean status,
			String type);

	ParkingSlot save(long id, boolean activityStatus);

	List<ParkingSlot> findByActivityStatus(boolean activityStatus);
}
