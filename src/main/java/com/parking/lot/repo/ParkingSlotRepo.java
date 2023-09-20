package com.parking.lot.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.parking.lot.entity.ParkingSlot;

@Repository
public interface ParkingSlotRepo extends JpaRepository<ParkingSlot, Long>, JpaSpecificationExecutor<ParkingSlot> {
	List<ParkingSlot> findByParkingStatusAndType(boolean status, String type);

	List<ParkingSlot> findByActivityStatusAndParkingStatusAndType(boolean activityStatus, boolean status, String type);

	List<ParkingSlot> findByActivityStatus(boolean activityStatus);
}
