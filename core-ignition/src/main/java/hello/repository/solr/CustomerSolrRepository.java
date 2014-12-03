package hello.repository.solr;

import hello.solr.CustomerSolr;

import org.springframework.data.solr.repository.SolrCrudRepository;

public interface CustomerSolrRepository extends CustomCustomerSolrRepository,
		SolrCrudRepository<CustomerSolr, String> {
}
