package hello.repository.jpa;

import hello.domain.Customer;
import hello.service.Authorities;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;

@Secured(Authorities.ROLE_USER)
public interface CustomerRepository extends
		PagingAndSortingRepository<Customer, String> {

	@Secured(Authorities.ROLE_ADMIN)
	List<Customer> findByLastName(@Param("name") String lastName);

	Page<Customer> findByLastName(@Param("name") String lastName,
			Pageable pageable);

	List<Customer> findByLastName(@Param("name") String lastName, Sort sort);
}