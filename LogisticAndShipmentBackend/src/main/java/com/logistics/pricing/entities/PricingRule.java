package com.logistics.pricing.entities;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import com.logistics.common.entities.BaseEntity;
import com.logistics.shipments.entities.Shipment;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name="pricing_rules")
@Getter
@Setter
@NoArgsConstructor
@AttributeOverride(name="id", column = @Column(name="rule_id"))
public class PricingRule extends BaseEntity{
	@Column(nullable = false)
	private String name;
	
	@Column(name = "base_charge", nullable = false)
	@PositiveOrZero
	private BigDecimal baseCharge;
	
	@Column(name="weight_rate", nullable = false)
	@PositiveOrZero
	private BigDecimal weightRate;
	
	@Column(name = "agent_commission_percentage", nullable = false)
	@PositiveOrZero
	@Max(100)
	private BigDecimal agentCommisionPercentage;
	
	@Column(name="minimum_charge")
	@PositiveOrZero
	private BigDecimal minimumCharge;
	
	@Column(nullable = false)
	@FutureOrPresent
	private LocalDate effectiveFrom;
	
	@OneToMany(mappedBy = "rule")
	@ToString.Exclude
	private List<Shipment> ruledShipments = new ArrayList<>();
}
