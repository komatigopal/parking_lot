package com.parking.lot.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.parking.lot.entity.Vehicle;

@Repository
public interface VehicleRepo extends JpaRepository<Vehicle, String>, JpaSpecificationExecutor<Vehicle> {

}
