package com.java.demo.streamapi.repos;

import com.java.demo.streamapi.models.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author yujiale
 */
@Repository
public interface ProductRepo extends CrudRepository<Product, Long> {

	@Override
	List<Product> findAll();
}
