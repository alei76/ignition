package hello.config.liquibase;

import java.util.HashMap;

import javax.sql.DataSource;

import liquibase.integration.spring.SpringLiquibase;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
//@Profile({ "test" })
@Slf4j
public class LiquibaseContextTest {
	@Autowired
	private DataSource dataSource;

	public LiquibaseContextTest() {
		log.debug("Creating LiquibaseContextTest");
	}

	@Bean(name = "liquibase")
	public SpringLiquibase liquibase() {
		SpringLiquibase liquibase = new SpringLiquibase();
		liquibase.setDataSource(dataSource);
		liquibase.setChangeLog("classpath:liquibase/changelog-master.xml");
		liquibase.setChangeLogParameters(new HashMap<String, String>());
		liquibase.setContexts("test");
		return liquibase;
	}
}
