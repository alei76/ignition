package hello.controller;

import hello.repository.jpa.CustomerRepository;
import hello.service.CustomerService;
import hello.service.ProductService;
//import hello.solr.CustomerSolr;
//import hello.solr.ProductSolr;
import hello.solr.CustomerSolr;
import hello.solr.ProductSolr;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * To test GET http://localhost:8181/solr/sample<br/>
 * Make sure SOLR is running and enabled in application.properties solr.active=true
 *
 * @author jameskolean
 *
 */
@RestController
public class PrintSolrController {

	@Autowired(required = false)
	protected CustomerService customerService = null;

	@Value("${solr.active}")
	protected boolean isSolrActive;

	@Autowired(required = false)
	protected ProductService productService = null;

	@Autowired(required = false)
	protected CustomerRepository repository = null;

	private String doSolr() {
		final StringBuffer sb = new StringBuffer();
		sb.append("Solr 'Smith' Customers found:\n");
		for (final CustomerSolr doc : customerService.search("Smith")) {
			sb.append(doc.toString()).append("\n");
		}
		sb.append("\nSolr page of 'bolt' Products found:\n");
		final Pageable pageSpecification = new PageRequest(0, 20, sortBySkuAsc());
		final Page<ProductSolr> productsPage = productService.search("bolt", pageSpecification);
		for (final ProductSolr productSolr : productsPage) {
			sb.append(productSolr.toString()).append("\n");
		}
		return sb.toString();
	}

	@RequestMapping("/solr/sample")
	public String solrSample() {
		String result = "Request failed";
		if (!isSolrActive) {
			result = "/solr/sample is disabled.";
		} else {
			try {
				result = doSolr();
			} catch (final Exception e) {
				e.printStackTrace();
			}
		}
		System.out.println(result);
		return result;
	}

	private Sort sortBySkuAsc() {
		return new Sort(Sort.Direction.ASC, "sku");
	}

}
