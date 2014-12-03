package hello.repository.solr;

import hello.solr.CustomerSolr;

import java.util.List;

/**
 * Example of custom methods
 * 
 * @author jameskolean
 *
 */
public interface CustomCustomerSolrRepository {
	public List<CustomerSolr> search(String searchTerm);

	public void update(CustomerSolr todoEntry);
}
