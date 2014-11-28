package hello.config;

import java.net.URI;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

@Import({ RepositoryRestMvcConfiguration.class, JpaConfiguration.class })
@ComponentScan(basePackages = { "hello.config.liquibase", "hello.controller" })
@EnableAutoConfiguration
public class Application extends RepositoryRestMvcConfiguration {
	public static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(Application.class, args);
	}

	@Override
	protected void configureRepositoryRestConfiguration(
			RepositoryRestConfiguration config) {
		config.setBaseUri(URI.create("/api"));
	}
}
