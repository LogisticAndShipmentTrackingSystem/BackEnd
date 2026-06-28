package com.logistics.cities.entities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringExclude;

import com.logistics.common.entities.BaseEntity;
import com.logistics.enums.Zone;
import com.logistics.shipments.entities.Shipment;
import com.logistics.users.entities.Address;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "cities")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AttributeOverride(name="id", column = @Column(name = "city_id"))
public class City extends BaseEntity{
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String state;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private Zone zone;
	
	@Column(nullable = false)
	private Double lattitide;
	
	@Column(nullable = false)
	private Double longitude;
	
	// Address mapping ---
	@OneToMany(mappedBy = "city")
	@ToStringExclude
	private List<Address> addresses = new ArrayList<>();
	// ---
	// shipments mapping
	@OneToMany(mappedBy = "sourceCity")
	@ToStringExclude
	private List<Shipment> sourceShipments = new ArrayList<>();
	
	@OneToMany(mappedBy = "destinationCity")
	@ToStringExclude
	private List<Shipment> destinationShipments = new ArrayList<>();
}
