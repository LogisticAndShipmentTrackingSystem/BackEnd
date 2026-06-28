package com.logistics.users.entities;

import java.util.ArrayList;
import java.util.List;

import com.logistics.cities.entities.City;
import com.logistics.common.entities.BaseEntity;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AttributeOverride(name = "id", column = @Column(name="address_id"))
public class Address extends BaseEntity{
	
	@Size(max = 50)
	private String lable;
	
	@Column(name="contact_person")
	private String contactPerson;
	
	@Column(name="contact_number", nullable = false)
	private String contactNumber;
	
	@Column(name="primary_address", nullable = false)
	private String primaryAddress;
	
	@Column(name="postal_code", nullable = false, length = 6)
	private Integer postalCode;
	
	/*
	 * connect table User @ManyToOne relation
	 * Owning table
	 */
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;
	/*
	 * connect table City to this one with @ManyToOne relation
	 * Owning table
	 */
	
	@ManyToOne
	@JoinColumn(name= "city_id", nullable = false)
	private City city;
	
	// relations ---
	
	@OneToMany(mappedBy = "pickupAdddress")
	@ToString.Exclude
	private List<Address> pickedShipments = new ArrayList<>();
	
	// ---
}
