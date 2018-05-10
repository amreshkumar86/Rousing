package com.oen.core.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.oen.core.domain.model.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
	
	@Query(" SELECT customerEntity from Customer customerEntity "
		 + " WHERE customerEntity.recordStatus = 1")
	List<Customer> getAllActiveCustomersObj();
	
	@Query(" SELECT customerEntity.id from Customer customerEntity "
		 + " WHERE customerEntity.recordStatus = 1")
	List<Long> getAllActiveCustomersIds();

}
