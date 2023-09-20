package com.parking.lot.entity;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Table(name = "parking_slot_location")
@EntityListeners(value = { AuditingEntityListener.class })
public class Location {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	private String buildingName;
	private long floor;
	private long number;
	private String navigation;

	public String settingNavigation() {
		navigation = "Go to " + buildingName + ", Floor No " + floor + " and Slot number " + number;
		return navigation;
	}

}
