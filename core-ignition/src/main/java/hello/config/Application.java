package hello.config;

import java.net.URI;
import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;

@ComponentScan(basePackages = { "hello.config.liquibase", "hello.controller" })
@EnableAutoConfiguration
@Import({ JpaConfiguration.class })
// RepositoryRestMvcConfiguration.class
public class Application extends RepositoryRestMvcConfiguration {
	public static ConfigurableApplicationContext context;

	public static void main(String[] args) {
		context = SpringApplication.run(Application.class, args);

		if (args.length == 1 && "beans".equalsIgnoreCase(args[0])) {
			System.out
					.println("Let's inspect the beans provided by Spring Boot:");

			String[] beanNames = context.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				System.out.println(beanName);
			}
		}
	}

	@Override
	protected void configureRepositoryRestConfiguration(
			RepositoryRestConfiguration config) {
		config.setBaseUri(URI.create("/api"));
	}
}
