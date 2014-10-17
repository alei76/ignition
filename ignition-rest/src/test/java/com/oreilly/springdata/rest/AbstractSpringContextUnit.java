package com.oreilly.springdata.rest;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import com.oreilly.springdata.rest.*;

/**
 * Base class for JUnit test classes that need a Spring context.
 */
// @TransactionConfiguration(transactionManager = "transactionManager",
// defaultRollback = false)
// @Transactional
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(loader = AnnotationConfigContextLoader.class, classes = { ApplicationConfig.class })
// @DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ActiveProfiles("test")
// @ActiveProfiles("local")
public abstract class AbstractSpringContextUnit {

	@Autowired
	private ApplicationContext applicationContext;

	/**
	 * Default constructor.
	 */
	public AbstractSpringContextUnit() {
	}

}