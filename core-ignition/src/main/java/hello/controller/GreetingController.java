package hello.controller;

import hello.domain.Greeting;
import hello.repository.jpa.CustomerRepository;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GreetingController {

	private static final String template = "Hello, %s %s!";
	private final AtomicLong counter = new AtomicLong();

	@Autowired
	protected CustomerRepository repository;

	@RequestMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "id") final String id) {
		final hello.domain.Customer customer = repository.findOne(id);
		return new Greeting(counter.incrementAndGet(), String.format(template, customer.getFirstName(), customer.getLastName()));
	}

}
