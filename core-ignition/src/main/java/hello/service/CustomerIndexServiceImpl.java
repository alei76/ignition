package hello.service;

import hello.domain.Customer;
import hello.repository.solr.CustomerSolrRepository;
import hello.solr.CustomerSolr;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
public class CustomerIndexServiceImpl implements CustomerIndexService {

	@Autowired
	CustomerSolrRepository repository;

	@Transactional
	@Override
	public void addToIndex(Customer customer) {
		CustomerSolr document = CustomerSolr.getBuilder(customer.getId())
				.firstName(customer.getFirstName())
				.lastName(customer.getLastName()).build();
		repository.save(document);

	}

	@Transactional
	@Override
	public void deleteFromIndex(String id) {
		// TODO Auto-generated method stub

	}

	@Transactional
	@Override
	public List<CustomerSolr> search(String searchTerm) {
		log.debug("Searching CustomerDocument with search term: {}", searchTerm);
		return repository.search(searchTerm);
	}

	@Transactional
	@Override
	public void update(Customer customer) {
		// TODO Auto-generated method stub

	}
}