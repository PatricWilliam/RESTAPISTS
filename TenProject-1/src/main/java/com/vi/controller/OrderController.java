package com.vi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.vi.model.Order;
import com.vi.repo.OrderRepostory;


@Controller
public class OrderController {

	@Autowired
	OrderRepostory orderRepo;

	public Order createOrder(Order order) {
		return orderRepo.save(order);
	}
}
