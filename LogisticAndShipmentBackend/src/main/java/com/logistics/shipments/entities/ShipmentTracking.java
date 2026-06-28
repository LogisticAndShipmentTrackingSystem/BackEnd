package com.logistics.shipments.entities;

import com.logistics.common.entities.BaseEntity;
import com.logistics.enums.DeliveryStatus;
import com.logistics.users.entities.User;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Table(name = "shipment_tracking_events")
@Entity
@Getter
@Setter
@NoArgsConstructor
@ToString
@AttributeOverride(name="id", column = @Column(name="shipment_tracking_id"))
public class ShipmentTracking extends BaseEntity{
	@ManyToOne
	@JoinColumn(name = "shipmnt_id", nullable = false)
	private Shipment shipment;
	
	@Column(nullable = false)
	private DeliveryStatus status;
	
	private String remarks;
	
	@ManyToOne
	@JoinColumn(name="updated_by")
	private User updatedBy;
}
