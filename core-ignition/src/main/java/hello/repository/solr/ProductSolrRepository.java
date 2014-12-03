package hello.repository.solr;

import hello.solr.ProductSolr;

import java.util.List;

import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface ProductSolrRepository extends
		SolrCrudRepository<ProductSolr, String> {
	@Query("text:?0")
	List<ProductSolr> findByText(String keywords);
}
