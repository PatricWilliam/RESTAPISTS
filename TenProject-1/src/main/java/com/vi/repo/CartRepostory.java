package com.vi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vi.model.Cart;

public interface CartRepostory extends JpaRepository<Cart,Integer> {

}
