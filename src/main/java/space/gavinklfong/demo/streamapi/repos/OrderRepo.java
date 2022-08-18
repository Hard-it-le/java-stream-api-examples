package space.gavinklfong.demo.streamapi.repos;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import space.gavinklfong.demo.streamapi.models.Order;

import java.util.List;

/**
 * @author yujiale
 */
@Repository
public interface OrderRepo extends CrudRepository<Order, Long> {

	@Override
	List<Order> findAll();
}
