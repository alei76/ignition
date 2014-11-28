package hello.solr;

import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.solr.repository.SolrCrudRepository;

@RepositoryRestResource()
public interface CustomerDocumentRepository extends
		SolrCrudRepository<CustomerDocument, String> {

}
