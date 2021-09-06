package com.vi.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.vi.model.CustomerDetails;
import com.vi.repo.CustomerRepostory;

@Controller
public class CustomerController {

	@Autowired
	public CustomerRepostory cusRepo;

	public CustomerDetails createCustomer(CustomerDetails customer) {
		return cusRepo.save(customer);
	}

	public CustomerDetails customerLogin(CustomerDetails customer) {
		if (cusRepo.findByUserName(customer.getUserName()) == null) {
			return null;
		}
		CustomerDetails cus = cusRepo.findByUserName(customer.getUserName());
		return cus;
	}

	public Optional<CustomerDetails> getCustomerId(long customerId) {
		return cusRepo.findById(customerId);
	}

	public CustomerDetails updateCustomer(CustomerDetails customer, long customerId) {

		CustomerDetails existingCustomer = cusRepo.findById(customerId).get();

		if (customer.getPassword() != null) {
			existingCustomer.setPassword(customer.getPassword());
		}

		if (customer.getFirstName() != null) {
			existingCustomer.setFirstName(customer.getFirstName());
		}

		if (customer.getLastName() != null) {
			existingCustomer.setLastName(customer.getLastName());
		}

		if (customer.getMiddleName() != null) {
			existingCustomer.setMiddleName(customer.getMiddleName());
		}

		if (customer.getPhone() != null) {
			existingCustomer.setPhnNo(customer.getPhone());
		}

		if (customer.getPhoneExtension() != null) {
			existingCustomer.setPhnExtension(customer.getPhoneExtension());
		}

		return cusRepo.save(existingCustomer);
	}

}
