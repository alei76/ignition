package hello.config;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
//@Profile({ "test" })
@Slf4j
public class LiquibaseContextTest {
	@Autowired
	private DataSource dataSource;
	@Resource
	private Environment environment;

	public LiquibaseContextTest() {
		log.debug("Creating LiquibaseContextTest");
	}

	@Bean(name = "liquibase")
	public SpringLiquibase liquibase() {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog(environment.getRequiredProperty("liquibase.change-log"));
		liquibase.setChangeLogParameters(new HashMap<String, String>());
		liquibase.setContexts(environment.getRequiredProperty("liquibase.contexts"));
		return liquibase;
	}
}
