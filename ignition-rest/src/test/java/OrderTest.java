import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import com.oreilly.springdata.rest.AbstractSpringContextUnit;
import com.oreilly.springdata.rest.order.Order;
import com.oreilly.springdata.rest.order.OrderRepository;

public class OrderTest extends AbstractSpringContextUnit {

	@Autowired
	OrderRepository orderRepository;

	@Test
	@Transactional
	@Rollback(true)
	public void doActiveCounts() throws Exception {
		Iterable<Order> findAll = orderRepository.findAll();
		for (Order order : findAll) {
			System.out.println(""+order);
		}
	}

}
