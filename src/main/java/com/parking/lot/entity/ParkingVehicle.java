
package com.parking.lot.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "parking_vehicle")
public class ParkingVehicle {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private boolean status;
	@Temporal(TemporalType.TIMESTAMP)
	private Date occupiedAt;
	@Temporal(TemporalType.TIMESTAMP)
	private Date releasedAt;
	@OneToOne(cascade = CascadeType.ALL)
	private Vehicle vehicle;
	@OneToOne(cascade = CascadeType.ALL)
	private ParkingSlot parkingSlot;
}
