package com.vi.model;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;

@Component
@Entity
@Table(name = "Address")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "address_identifier")
	@SequenceGenerator(sequenceName = "address_identifier", allocationSize = 1000, name = "address_identifier")
	public long addressIdentifier;

	@Column(nullable = false, length = 10)
	public int houseNo;

	@Column(length = 250)
	public String street;

	@Column(length = 250)
	public String area;

	@Column(length = 250)
	public String city;

	@Column(length = 250)
	public String State;

	@Column(length = 250)
	public String country;

	@Column(length = 20)
	public long pincode;

	@OneToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "customerId", nullable = false)
	@JsonBackReference
	@Schema(hidden = true)
	public CustomerDetails customerDetails;

	public long getAddressIdentifier() {
		return addressIdentifier;
	}

	public void setAddressIdentifier(long addressIdentifier) {
		this.addressIdentifier = addressIdentifier;
	}

	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	public int getHouseNo() {
		return houseNo;
	}

	public void setHouseNo(int houseNo) {
		this.houseNo = houseNo;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getArea() {
		return area;
	}

	public void setArea(String area) {
		this.area = area;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public long getPincode() {
		return pincode;
	}

	public void setPincode(long pincode) {
		this.pincode = pincode;
	}



}
