package hello.solr;

import hello.domain.Customer;

public interface CustomerIndexService {

	public void addToIndex(Customer customer);

	public void deleteFromIndex(Customer customer);
}