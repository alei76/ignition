package hello.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, String> {

	List<Customer> findByLastName(String lastName);
}