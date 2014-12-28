package hello.config;

import java.util.Arrays;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Import;

@ComponentScan(basePackages = { "hello.controller", "hello.service" })
@EnableAutoConfiguration
@Import({ JpaConfiguration.class, SolrConfiguration.class , SecurityConfiguration.class })
// RepositoryRestMvcConfiguration.class
public class Application {
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

}
