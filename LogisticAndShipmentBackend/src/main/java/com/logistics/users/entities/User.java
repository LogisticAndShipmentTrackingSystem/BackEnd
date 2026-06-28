package com.logistics.users.entities;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.builder.ToStringExclude;

import com.logistics.agents.entities.AgentProfile;
import com.logistics.common.entities.BaseEntity;
import com.logistics.complaints.entities.Complaint;
import com.logistics.enums.UserRole;
import com.logistics.shipments.entities.Shipment;
import com.logistics.shipments.entities.ShipmentTracking;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="users")
@Getter
@Setter
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name="user_id"))
@ToString
public class User extends BaseEntity{
	
	@Column(name="full_name", nullable = false)
	private String fullName;
	
	@Column(nullable = false)
	@Email
	private String email;
	
	@Column(nullable = false)
	/*
	 * passwords must be hashed, 
	 * currently not hashed phase.
	 */
	private String password;
	
	@Column(name = "phone_number", nullable =false)
	private String phoneNumber;
	
	@Enumerated(EnumType.STRING)
	@Column(nullable = false)
	private UserRole role;
	
	@OneToOne(mappedBy = "user")
	@ToString.Exclude
	private AgentProfile agentProfile;
	
	@OneToMany(mappedBy = "user")
	@ToString.Exclude
	private List<Address> addresses = new ArrayList<>();
	
	// shipments relations ---
	@OneToMany(mappedBy = "customer")
	@ToStringExclude
	private List<Shipment> customer_shipments = new ArrayList<>();
	
	
	@OneToMany(mappedBy = "assignedAgent")
	@ToStringExclude
	private List<Shipment> agent_shipments = new ArrayList<>();

	// ---
	// ShipmentTracking ---
	@OneToMany(mappedBy = "updatedBy")
	@ToStringExclude
	private List<ShipmentTracking> updatedTracking = new ArrayList<>();
	// ---
	
	// Complaints relations ---
	@OneToMany(mappedBy = "customer")
	@ToStringExclude
	private List<Complaint> customerComplaints = new ArrayList<>();
	
	@OneToMany(mappedBy = "assignedTo")
	@ToStringExclude
	private List<Complaint> assignedComplaints = new ArrayList<>();
	// ---
}
