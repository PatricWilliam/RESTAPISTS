package com.vi.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.vi.model.CustomerDetails;

public interface CustomerRepostory extends JpaRepository<CustomerDetails, Long>{ 
	
	CustomerDetails findByUserName(String userName);

}
