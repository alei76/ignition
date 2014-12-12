package hello.repository.solr;

import hello.solr.ProductSolr;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;

public interface ProductSolrRepository extends
		SolrCrudRepository<ProductSolr, String> {
	@Query("text:?0")
	Page<ProductSolr> findByText(String searchTerm, Pageable pageable);
}
