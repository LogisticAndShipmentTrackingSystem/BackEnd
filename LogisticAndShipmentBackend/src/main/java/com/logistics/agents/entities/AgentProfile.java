package com.logistics.agents.entities;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.data.annotation.CreatedDate;

import com.logistics.users.entities.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="agent_profiles")
@Getter
@Setter
@ToString(callSuper = true)
@NoArgsConstructor
public class AgentProfile {
	
	@OneToOne(cascade= CascadeType.ALL)
	@MapsId
	@JoinColumn(name="profile_id", nullable = false)
	@ToString.Exclude
	private User user;
	
	@Column(name="employee_code", nullable = false)
	private String employeeCode;
	
	@Column(name="join_date", nullable = false)
	@CreatedDate
	private LocalDate joinDate;
	
	@Column(nullable = false)
	private BigDecimal commission;
	
	@Column(name="aadhaar_number", nullable = false)
	private String aadhaarNumber;
}
