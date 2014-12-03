package hello.service;

import hello.solr.CustomerSolr;

import java.util.List;

public interface CustomerService {
	/**
	 * Searches the customer for the given search term.
	 * 
	 * @param searchTerm
	 * @return The list of customer documents. If no matches are not found, the
	 *         method returns an empty list.
	 */
	public List<CustomerSolr> search(String searchTerm);

}