package hello.controller;

import hello.domain.Customer;
import hello.domain.Greeting;
import hello.repository.jpa.CustomerRepository;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;

import static org.hamcrest.Matchers.equalTo;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class GreetingControllerTest {
	GreetingController greetingController;

	@Before
	public void setUp() {
		greetingController = new GreetingController();
	}

	@Test
	public void whenGreetingPassedIdGreetingIsReturned() {
		greetingController.repository = mock(CustomerRepository.class);
		final Customer customer = new Customer("James", "Kolean");
		when(greetingController.repository.findOne("id")).thenReturn(customer);

		final Greeting greeting = greetingController.greeting("id");

		assertThat(1L, equalTo(greeting.getId()));
		assertThat(greeting.getContent(), equalTo("Hello, James Kolean!"));
	}
}
