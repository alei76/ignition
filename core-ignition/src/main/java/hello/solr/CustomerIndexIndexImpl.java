package hello.solr;

import hello.repository.jpa.Customer;

import javax.annotation.Resource;
import javax.persistence.EntityNotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerIndexIndexImpl implements CustomerIndexService {

	@Resource
	private CustomerDocumentRepository repository;

	@Transactional
	@Override
	public void addToIndex(Customer customer) {
		CustomerDocument document = CustomerDocument
				.getBuilder(customer.getId())
				.firstName(customer.getFirstName()).lastName(customer.getLastName()).build();
		repository.save(document);

	}

	@Transactional
	@Override
	public void deleteFromIndex(Customer customer)  throws EntityNotFoundException {
		repository.delete(customer.getId());
	}
}