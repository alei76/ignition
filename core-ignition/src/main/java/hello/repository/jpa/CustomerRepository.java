package hello.repository.jpa;

import hello.domain.Customer;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

public interface CustomerRepository extends
		PagingAndSortingRepository<Customer, String> {

	@RestResource(path = "byLastName")
	List<Customer> findByLastName(@Param("name") String lastName);

	@RestResource(path = "byLastNamePaging")
	Page<Customer> findByLastName(@Param("name") String lastName,
			Pageable pageable);

	@RestResource(path = "byLastNameSorting")
	List<Customer> findByLastName(@Param("name") String lastName, Sort sort);
}