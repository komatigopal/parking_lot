/*
 * package com.parking.lot.dto;
 * 
 * import com.parking.lot.entity.ParkingSlot; import
 * com.parking.lot.entity.Vehicle;
 * 
 * public class VehicleDto extends Vehicle { private ParkingSlot parkingSlot;
 * private Vehicle vehicle;
 * 
 * 
 * public static ParkingSlot toDto(ParkingSlot parkingSlot, Vehicle vehicle) {
 * parkingSlot.setVehicles(parkingSlot.getVehicles().stream().filter(v ->
 * v.getId().equals(vehicle.getId())) .collect(Collectors.toList()));
 * parkingSlot return parkingSlot; }
 * 
 * }
 */