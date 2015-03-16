package hello.service;

import hello.repository.solr.ProductSolrRepository;
import hello.solr.ProductSolr;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
	@Autowired(required = false)
	ProductSolrRepository repository;

	// @PreAuthorize("hasPermission('Customer', 'search')")
	@Override
	public Page<ProductSolr> search(final String searchTerm, final Pageable pageable) {
		log.debug("Searching ProductSolr with search term: {}", searchTerm);
		return repository.findByText(searchTerm, pageable);
	}

}