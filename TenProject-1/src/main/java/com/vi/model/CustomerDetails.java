package com.vi.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;

import org.springframework.stereotype.Component;

@Component //create bean
@Entity // JPA mapping for table in the Db
@Table(name = "CustomerDetails")
public class CustomerDetails {
	@Id// primary key
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "customer_id")
	@SequenceGenerator(sequenceName = "customer_id", allocationSize = 100, name = "customer_id")
	public long customerId;
	
	@Column(nullable = false, length = 250)
	public String firstName;

	@Column(length = 250)
	public String middleName;

	@Column(length = 250)
	public String lastName;

	@Column(nullable = false)
	public String phoneExtension;

	@Column(nullable = false, length = 25)
	public String phone;

	@Column(nullable = false, unique = true, length = 250)
	public String emailId;

	@Column(nullable = false, unique = true, length = 250)
	@NotBlank
	public String userName;

	@Column(nullable = false, length = 250)
	@NotBlank
	public String password;

	@OneToOne(mappedBy = "customerDetails", cascade = CascadeType.ALL)
	public Address address;
	
	@OneToOne(mappedBy = "customerDetails", cascade = CascadeType.ALL)
	public Cart cart;

	@OneToMany(mappedBy = "customerDetails", cascade = CascadeType.ALL)
	public List<Order> order;

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
		address.setCustomerDetails(this);
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
		cart.setCustomerDetails(this);
	}

	public List<Order> getOrder() {
		return order;
	}

	public void setOrder(List<Order> order) {
		this.order = order;
		for (Order o : order) {
			o.setCustomerDetails(this);
		}

	}

	public long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(int customerId) {
		this.customerId = customerId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getPhoneExtension() {
		return phoneExtension;
	}

	public void setPhnExtension(String phoneExtension) {
		this.phoneExtension = phoneExtension;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhnNo(String phone) {
		this.phone = phone;
	}

	public String getEmailId() {
		return emailId;
	}

	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}

	

}
