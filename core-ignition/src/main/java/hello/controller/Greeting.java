package hello.controller;

import hello.domain.CustomerRepository;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

public class Greeting {

	private final long id;
	private final String content;

	public Greeting(long id, String content) {
		this.id = id;
		this.content = content;
	}

	public long getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

}
