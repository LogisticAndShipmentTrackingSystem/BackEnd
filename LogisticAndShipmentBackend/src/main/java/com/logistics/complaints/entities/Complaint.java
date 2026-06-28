package com.logistics.complaints.entities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.ColumnDefault;

import com.logistics.common.entities.BaseEntity;
import com.logistics.enums.ComplaintStatus;
import com.logistics.enums.ComplaintType;
import com.logistics.shipments.entities.Shipment;
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
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="complaints")
@Getter
@Setter
@NoArgsConstructor
@AttributeOverride(name="id", column = @Column(name="complaint_id"))
public class Complaint extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name = "shipment_id", nullable = false)
	private Shipment shipment;
	
	@ManyToOne
	@JoinColumn(name="customer_id", nullable = false)
	private User customer;
	
	// specifically User is Admin who manages this complaint.
	// Assigned to Zonal Admin. using zone of the shipment as per status it is managed.
	@ManyToOne
	@JoinColumn(name="assigned_to", nullable = false)
	private User assignedTo;
	
	@Column(name="complaint_type",nullable = false)
	@Enumerated(EnumType.STRING)
	private ComplaintType complaintType;
	
	private String description;
	
	// updated by assigned Admin
	@Column(name = "resolution_remarks")
	private String resolutionRemarks;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	@ColumnDefault(value = "OPEN")
	private ComplaintStatus status;
	
	// relations ---
	
	@OneToMany(mappedBy = "complaint")
	@ToString.Exclude
	private List<ComplaintAttachment> attachments = new ArrayList<>();
	// ---
}
