package com.logistics.shipments.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.logistics.cities.entities.City;
import com.logistics.common.entities.BaseEntity;
import com.logistics.complaints.entities.Complaint;
import com.logistics.enums.DeliveryStatus;
import com.logistics.payments.entities.Payment;
import com.logistics.pricing.entities.PricingRule;
import com.logistics.users.entities.Address;
import com.logistics.users.entities.User;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="shipments")
@Getter
@Setter
@NoArgsConstructor
@ToString
@AttributeOverride(name = "id", column = @Column(name="shipment_id"))
public class Shipment extends BaseEntity{
	/*
	 * tracking ID is human understandable ID for each shipment status checking.
	 * tracking ID is composed of (SOURCE CITY)+(UniqueIdentifier)+(Destination city).
	 * Only using this shipments must be tracked from application.
	 */
	@Column(name="traking_id",nullable = false)
	private String trackingId;
	
	@ManyToOne
	@JoinColumn(name="customer_id", nullable = false)
	private User customer;
	
	
	@ManyToOne
	@JoinColumn(name="assigned_agent")
	private User assignedAgent;
	
	@ManyToOne
	@JoinColumn(name="pickup_address_id", nullable = false)
	private Address pickupAdddress;
	
	@Column(name="destination_address", nullable = false)
	private String deliveryAddress;
	
	@ManyToOne
	@JoinColumn(name="source_city_id", nullable = false)
	private City sourceCity;
	
	@ManyToOne
	@JoinColumn(name="destination_city_id", nullable = false)
	private City destinationCity;

	@Column(name="package_type")
	private String packgeType;
	
	@Column(length = 500)
	private String description;
	
	@Column(nullable = false)
	private BigDecimal weight;
	
	/*
	 * A calculated field.
	 * Programmer may ask for specifications as
	 * height, length, width etc. and determine volume. 
	 */
	@Column(nullable = false)
	private BigDecimal volume;
	
	@ManyToOne
	@JoinColumn(name="pricing_rule_id", nullable = false)
	private PricingRule rule;
	
	/*
	 * kept for better data retrieval and to reduce stress to 
	 * calculate this field from different API calls in repetitive manner.
	 */
	@Column(name="shipping_charge",nullable = false)
	@PositiveOrZero
	private BigDecimal shippingCharge;
	
	@Column(name="expected_date")
	@FutureOrPresent
	private LocalDate expectedDate;
	@FutureOrPresent
	@Column(name="actual_date")
	private LocalDate actualDate;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@ColumnDefault("BOOKED")
	private DeliveryStatus status;
	
	// relations ---
	
	@OneToMany(mappedBy = "shipment")
	@ToString.Exclude
	private List<ShipmentTracking> trackings = new ArrayList<>();
	
	@OneToMany(mappedBy = "shipment")
	@ToString.Exclude
	private List<Payment> payments = new ArrayList<>();
	
	@OneToMany(mappedBy = "shipment")
	@ToString.Exclude
	private List<Complaint> complaints = new ArrayList<>();
	
	// ---
}
