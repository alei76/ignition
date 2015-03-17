package hello.controller;

import hello.domain.Customer;
import hello.repository.jpa.CustomerRepository;
import hello.service.CustomerService;
import hello.service.ProductService;
//import hello.solr.CustomerSolr;
//import hello.solr.ProductSolr;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * To test GET http://localhost:8181/jpa/sample<br/>
 * Data is loaded from src/main/resources/db/changelog/db.changelog-master.xml. Create an empty schema called ignition and Liquibase will create and load the
 * tables.
 *
 *
 * @author jameskolean
 *
 */
@RestController
public class PrintJpaController {

	@Autowired
	CustomerService customerService;

	@Autowired
	ProductService productService;

	@Autowired
	CustomerRepository repository;

	/**
	 * @return info string. Note in a real controller this will be a bean that will be auto converted to JSON
	 */
	protected String doJpa() {
		final StringBuffer sb = new StringBuffer();
		// fetch all customers
		final Iterable<Customer> customers = repository.findAll();
		sb.append("Customers found with findAll():\n");
		System.out.println("-------------------------------\n");
		String anId = "";
		for (final Customer customer : customers) {
			sb.append(customer).append("\n");
			anId = customer.getId();
		}
		sb.append("\n");

		// fetch an individual customer by ID
		final Customer customer = repository.findOne(anId);
		sb.append(String.format("Customer found with findOne(%s):", anId));
		sb.append("\n--------------------------------\n");
		sb.append(customer);

		// fetch customers by last name
		final List<Customer> bauers = repository.findByLastName("Bauer");
		sb.append("\nCustomer found with findByLastName('Bauer'):\n");
		sb.append("--------------------------------------------\n");
		for (final Customer bauer : bauers) {
			sb.append(bauer).append("\n");
		}
		return sb.toString();
	}

	@RequestMapping("/jpa/sample")
	public String greeting() {
		String result = "Request failed";
		try {
			result = doJpa();
		} catch (final Exception e) {
			e.printStackTrace();
		}
		return result;
	}

}
