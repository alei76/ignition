package hello.service;

import hello.repository.solr.ProductSolrRepository;
import hello.solr.ProductSolr;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
	@Autowired
	ProductSolrRepository repository;

	// @PreAuthorize("hasPermission('Customer', 'search')")
	@Override
	public List<ProductSolr> search(String searchTerm) {
		log.debug("Searching ProductSolr with search term: {}", searchTerm);
		return repository.findByText(searchTerm);
	}

}