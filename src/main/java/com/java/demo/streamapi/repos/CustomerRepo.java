package com.java.demo.streamapi.repos;

import com.java.demo.streamapi.models.Customer;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yujiale
 */
@Repository
public interface CustomerRepo extends CrudRepository<Customer, Long> {

	@Override
	List<Customer> findAll();
}
