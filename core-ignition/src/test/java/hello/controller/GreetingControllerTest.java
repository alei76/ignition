package hello.controller;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import hello.domain.Customer;
import hello.repository.jpa.CustomerRepository;

import org.junit.Before;
import org.junit.Test;

public class GreetingControllerTest {
	GreetingController greetingController;

	@Before
	public void setUp() {
		greetingController = new GreetingController();
	}

	@Test
	public void test() {
		greetingController.repository = mock(CustomerRepository.class);
		final Customer customer = new Customer("James", "Kolean");
		when(greetingController.repository.findOne("id")).thenReturn(customer);

		final Greeting greeting = greetingController.greeting("id");

		assertThat(1L, equalTo(greeting.getId()));
		assertThat("Hello, James Kolean!", equalTo(greeting.getContent()));
	}
}
