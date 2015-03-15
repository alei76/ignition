package hello.controller;

import hello.domain.Customer;
import hello.repository.jpa.CustomerRepository;
import hello.service.CustomerService;
import hello.service.ProductService;
//import hello.solr.CustomerSolr;
//import hello.solr.ProductSolr;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	CustomerService customerService;

	@Autowired
	ProductService productService;

	@Autowired
	CustomerRepository repository;

	private void doJpa() {

		// // save a couple of customers
		// repository.save(new Customer("Jack", "Bauer"));
		// repository.save(new Customer("Chloe", "O'Brian"));
		// repository.save(new Customer("Kim", "Bauer"));
		// repository.save(new Customer("David", "Palmer"));
		// repository.save(new Customer("Michelle", "Dessler"));

		// fetch all customers
		final Iterable<Customer> customers = repository.findAll();
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		String anId = "";
		for (final Customer customer : customers) {
			System.out.println(customer);
			anId = customer.getId();
		}
		System.out.println();

		// fetch an individual customer by ID
		final Customer customer = repository.findOne(anId);
		System.out.println(String.format("Customer found with findOne(%s):", anId));
		System.out.println("--------------------------------");
		System.out.println(customer);
		System.out.println();

		// fetch customers by last name
		final List<Customer> bauers = repository.findByLastName("Bauer");
		System.out.println("Customer found with findByLastName('Bauer'):");
		System.out.println("--------------------------------------------");
		for (final Customer bauer : bauers) {
			System.out.println(bauer);
		}
	}

	// private void doSolr() {
	// System.out.println("Solr Customers found:");
	// for (final CustomerSolr doc : customerService.search("Smith")) {
	// System.out.println(doc.toString());
	// }
	// System.out.println("Solr Products found:");
	// final Pageable pageSpecification = new PageRequest(0, 20, sortBySkuAsc());
	// final Page<ProductSolr> productsPage = productService.search("bolt", pageSpecification);
	// for (final ProductSolr productSolr : productsPage) {
	// System.out.println(productSolr.toString());
	// }
	// }

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") final String name) {
		try {
			doJpa();
			// doSolr();
		} catch (final Exception e) {
			e.printStackTrace();
		}

		return new Greeting(counter.incrementAndGet(), String.format(template, name));
	}

	private Sort sortBySkuAsc() {
		return new Sort(Sort.Direction.ASC, "sku");
	}

}
