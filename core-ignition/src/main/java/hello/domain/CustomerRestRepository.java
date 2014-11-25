package hello.domain;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource()
public interface CustomerRestRepository extends PagingAndSortingRepository<Customer, String> {

	List<Customer> findByLastName(@Param("name") String name);

}