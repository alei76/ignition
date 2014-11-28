package hello.solr;

import hello.repository.jpa.Customer;

public interface CustomerIndexService {
	 
    public void addToIndex(Customer customer);
 
    public void deleteFromIndex(Customer customer);
}