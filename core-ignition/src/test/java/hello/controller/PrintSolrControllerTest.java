package hello.controller;

import hello.config.Application;
import hello.infrastructure.Cast;
import hello.service.CustomerService;
import hello.service.ProductService;
import hello.solr.CustomerSolr;
import hello.solr.ProductSolr;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Matchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.ConfigFileApplicationContextInitializer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import com.google.common.collect.Lists;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ActiveProfiles({ "test" })
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { Application.class }, initializers = ConfigFileApplicationContextInitializer.class)
public class PrintSolrControllerTest {

	@Autowired
	PrintSolrController printSolrController;

	@Test
	public void whenSolrSampleIsCalledReturnsString() {
		printSolrController.isSolrActive = true;
		printSolrController.productService = mock(ProductService.class);
		printSolrController.customerService = mock(CustomerService.class);
		final List<CustomerSolr> customerSolrDocs = Lists.newArrayList();
		when(printSolrController.customerService.search(Matchers.anyString())).thenReturn(customerSolrDocs);
		final Page<ProductSolr> productSolrPage = Cast.cast(mock(Page.class));
		final List<ProductSolr> productSolrDocs = Lists.newArrayList();
		when(productSolrPage.iterator()).thenReturn(productSolrDocs.iterator());
		when(printSolrController.productService.search(Matchers.anyString(), Matchers.any(Pageable.class))).thenReturn(productSolrPage);

		final String result = printSolrController.solrSample();

		assertThat(result, equalTo("Solr 'Smith' Customers found:\n\nSolr page of 'bolt' Products found:\n"));
	}
}
