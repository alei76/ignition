package hello.controller;

import hello.repository.jpa.Customer;
import hello.repository.jpa.CustomerRepository;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s!";
	private final AtomicLong counter = new AtomicLong();

	@RequestMapping("/greeting")
	public Greeting greeting(
			@RequestParam(value = "name", defaultValue = "World") String name) {
		try {
			doJpa();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new Greeting(counter.incrementAndGet(), String.format(template,
				name));
	}

	@Autowired
	CustomerRepository repository;

	private void doJpa() {
//		// save a couple of customers
//		System.out.println("AAAAAAAAAAAAAAA" + repository);
//		repository.save(new Customer("Jack", "Bauer"));
//		System.out.println("AAAAAAAAAAAAAAA");
//		repository.save(new Customer("Chloe", "O'Brian"));
//		System.out.println("AAAAAAAAAAAAAAA");
//		repository.save(new Customer("Kim", "Bauer"));
//		System.out.println("AAAAAAAAAAAAAAA");
//		repository.save(new Customer("David", "Palmer"));
//		System.out.println("AAAAAAAAAAAAAAA");
//		repository.save(new Customer("Michelle", "Dessler"));
//		System.out.println("AAAAAAAAAAAAAAA");

		// fetch all customers
		Iterable<Customer> customers = repository.findAll();
		System.out.println("Customers found with findAll():");
		System.out.println("-------------------------------");
		String anId = "";
		for (Customer customer : customers) {
			System.out.println(customer);
			anId = customer.getId();
		}
		System.out.println();

		// fetch an individual customer by ID
		Customer customer = repository.findOne(anId);
		System.out.println(String.format("Customer found with findOne(%s):",
				anId));
		System.out.println("--------------------------------");
		System.out.println(customer);
		System.out.println();

		// fetch customers by last name
		List<Customer> bauers = repository.findByLastName("Bauer");
		System.out.println("Customer found with findByLastName('Bauer'):");
		System.out.println("--------------------------------------------");
		for (Customer bauer : bauers) {
			System.out.println(bauer);
		}
	}

}
