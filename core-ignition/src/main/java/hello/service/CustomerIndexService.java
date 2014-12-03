package hello.service;

import hello.domain.Customer;
import hello.solr.CustomerSolr;

import java.util.List;

public interface CustomerIndexService {

	public void addToIndex(Customer customer);

	public void deleteFromIndex(String id);

	public List<CustomerSolr> search(String searchTerm);

	public void update(Customer customer);
}