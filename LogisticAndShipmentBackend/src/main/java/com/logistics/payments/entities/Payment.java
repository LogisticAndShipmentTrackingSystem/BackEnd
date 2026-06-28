package com.logistics.payments.entities;

import java.math.BigDecimal;

import com.logistics.common.entities.BaseEntity;
import com.logistics.enums.PaymentMethod;
import com.logistics.enums.PaymentStatus;
import com.logistics.shipments.entities.Shipment;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="payments")
@Getter
@Setter
@NoArgsConstructor
@AttributeOverride(name="id", column = @Column(name="payment_id"))
public class Payment extends BaseEntity{
	
	@ManyToOne
	@JoinColumn(name="shipment_id", nullable = false)
	private Shipment shipment;
	
	@Column(name="transaction_id", nullable = false)
	private String transactionId;
	
	@Column(nullable = false)
	@PositiveOrZero
	private BigDecimal amount;
	
	@Column(name="payment_method",nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentMethod paymentMethod;
	
	@Column(name="gateway")
	private String gatewayName;
	
	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;
}
