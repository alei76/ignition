package com;

import javax.sql.DataSource;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
//@EnableAutoConfiguration
public class Application {

//	public static void main(String[] args) {
//		SpringApplication.run(Application.class, args);
//	}

//	/**
//	 * Bootstraps an in-memory HSQL database.
//	 * 
//	 * @return
//	 * @see http 
//	 *      ://static.springsource.org/spring/docs/3.1.x/spring-framework-reference
//	 *      /html/jdbc.html#jdbc-embedded-database -support
//	 */
//	@Bean
//	public DataSource dataSource() {
//		return new SimpleDriverDataSource() {
//			{
//				setDriverClass(com.mysql.jdbc.Driver.class);
//				setUrl("jdbc:mysql://localhost/ignition");
//				setUsername("ignition");
//				setPassword("ignition");
//			}
//		};
//	}

//	/**
//	 * Sets up a {@link LocalContainerEntityManagerFactoryBean} to use
//	 * Hibernate. Activates picking up entities from the project's base package.
//	 *
//	 * @return
//	 */
//	@Bean
//	public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
//
//		HibernateJpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
//		vendorAdapter.setDatabase(Database.MYSQL);
//		// vendorAdapter.setDatabase(Database.HSQL);
//		vendorAdapter.setGenerateDdl(true);
//
//		LocalContainerEntityManagerFactoryBean factory = new LocalContainerEntityManagerFactoryBean();
//		factory.setJpaVendorAdapter(vendorAdapter);
//		factory.setPackagesToScan(getClass().getPackage().getName());
//		factory.setDataSource(dataSource());
//
//		return factory;
//	}
//
//	@Bean
//	public PlatformTransactionManager transactionManager() {
//
//		JpaTransactionManager txManager = new JpaTransactionManager();
//		txManager.setEntityManagerFactory(entityManagerFactory().getObject());
//		return txManager;
//	}

}
