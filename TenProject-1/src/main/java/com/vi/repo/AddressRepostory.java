package com.vi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vi.model.Address;

public interface AddressRepostory extends JpaRepository<Address, Long> {

}
