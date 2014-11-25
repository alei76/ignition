package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories
@EnableTransactionManagement
@ComponentScan(basePackages = { "hello.config.liquibase","hello.domain","hello.controller" })
@EnableAutoConfiguration
public class Application {
public static ConfigurableApplicationContext context;
	public static void main(String[] args) {
		 context = SpringApplication.run(Application.class, args);
	}
}
