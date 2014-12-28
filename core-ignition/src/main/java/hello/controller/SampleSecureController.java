package hello.controller;

import hello.domain.CurrentlyLoggedUser;
import hello.domain.DomainUser;

import java.io.Serializable;

import lombok.Data;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAuthority('ROLE_DOMAIN_USER')")
public class SampleSecureController { // extends ApiController{

	@Data
	class HelloResponse implements Serializable {
		private static final long serialVersionUID = 1L;
		String hello = "Sam";
	}

	@RequestMapping("/hello")
	public HelloResponse hello(@CurrentlyLoggedUser final DomainUser domainUser) {
		// return "hello";
		return new HelloResponse();
	}
}
