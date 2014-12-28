package hello.config;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan(basePackages = { "hello.controller" })
@EnableAutoConfiguration
@Import({ SecurityConfiguration.class })
public class Application {

	public static ConfigurableApplicationContext context;

	protected static void inspectBeans() {
		System.out.println("Let's inspect the beans provided by Spring Boot:");

		final String[] beanNames = context.getBeanDefinitionNames();
		Arrays.sort(beanNames);
		for (final String beanName : beanNames) {
			System.out.println(beanName);
		}
	}

	public static void main(final String[] args) {

		context = SpringApplication.run(Application.class, args);

		// inspectBeans();
	}
}
