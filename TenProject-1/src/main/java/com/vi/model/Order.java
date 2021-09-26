package com.vi.model;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.v3.oas.annotations.media.Schema;

@Component
@Entity
@Table(name = "OrderDetails")
public class Order {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_id")
	@SequenceGenerator(sequenceName = "order_id", allocationSize = 30, name = "order_id")
	public int orderId;

	@ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "customerId", nullable = false)
	@JsonBackReference
	@Schema(hidden = true)
	public CustomerDetails customerDetails;

	@Column
	public boolean isRegistrationLive;

	@Column
	public Date registrationDate;

	@Column(nullable = false)
	@Enumerated(EnumType.STRING)
	public TransactionType transactionType;

	@Column
	public double amountPaid;

	@OneToMany(mappedBy = "order", cascade = CascadeType.ALL)
	public List<OrderCourseRelation> orderCourses;

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	public boolean isRegistrationLive() {
		return isRegistrationLive;
	}

	public void setRegistrationLive(boolean isRegistrationLive) {
		this.isRegistrationLive = isRegistrationLive;
	}

	public Date getRegistrationDate() {
		return registrationDate;
	}

	public void setRegistrationDate(Date registrationDate) {
		this.registrationDate = registrationDate;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	public double getAmountPaid() {
		return amountPaid;
	}

	public void setAmountPaid(double amountPaid) {
		this.amountPaid = amountPaid;
	}

	public List<OrderCourseRelation> getOrderCourses() {
		return orderCourses;
	}

	public void setOrderCourses(List<OrderCourseRelation> orderCourses) {
		this.orderCourses = orderCourses;

		for (OrderCourseRelation order : orderCourses) {
			order.setOrder(this);
		}
	}

}
