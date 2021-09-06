package com.vi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vi.model.Order;

public interface OrderRepostory extends JpaRepository<Order, Integer>{

}
