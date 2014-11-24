package hello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaRepositories
@EnableTransactionManagement
@ComponentScan
@EnableAutoConfiguration

public class Application {
public static ConfigurableApplicationContext context;
	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		// ConfigurableApplicationContext context = SpringApplication.run(Application.class);
		// CustomerRepository repository = context.getBean(CustomerRepository.class);
		//
		// // save a couple of customers
		// repository.save(new Customer("Jack", "Bauer"));
		// repository.save(new Customer("Chloe", "O'Brian"));
		// repository.save(new Customer("Kim", "Bauer"));
		// repository.save(new Customer("David", "Palmer"));
		// repository.save(new Customer("Michelle", "Dessler"));
		//
		// // fetch all customers
		// Iterable<Customer> customers = repository.findAll();
		// System.out.println("Customers found with findAll():");
		// System.out.println("-------------------------------");
		// for (Customer customer : customers) {
		// System.out.println(customer);
		// }
		// System.out.println();
		//
		// // fetch an individual customer by ID
		// Customer customer = repository.findOne(1L);
		// System.out.println("Customer found with findOne(1L):");
		// System.out.println("--------------------------------");
		// System.out.println(customer);
		// System.out.println();
		//
		// // fetch customers by last name
		// List<Customer> bauers = repository.findByLastName("Bauer");
		// System.out.println("Customer found with findByLastName('Bauer'):");
		// System.out.println("--------------------------------------------");
		// for (Customer bauer : bauers) {
		// System.out.println(bauer);
		// }
		//
		// context.close();

	}

	// @Bean
	// public DataSource dataSource() {
	//
	// EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
	// return builder.setType(EmbeddedDatabaseType.HSQL).build();
	// }
	//
	// @Bean
	// public EntityManagerFactory entityManagerFactory() {
	// HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
	// vendorAdapter.setGenerateDdl(true);
	//
	// LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
	// factory.setJpaVendorAdapter(vendorAdapter);
	// factory.setPackagesToScan("hello.domain");
	// factory.setDataSource(dataSource());
	// factory.afterPropertiesSet();
	//
	// return factory.getObject();
	// }
	//
	// @Bean
	// public PlatformTransactionManager transactionManager() {
	//
	// JpaTransactionManager txManager = new JpaTransactionManager();
	// txManager.setEntityManagerFactory(entityManagerFactory());
	// return txManager;
	// }
}
