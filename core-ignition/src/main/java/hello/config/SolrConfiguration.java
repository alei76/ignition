package hello.config;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;

//@Configuration
//@ComponentScan(basePackages = { "hello.solr" })
//@EnableSolrRepositories(multicoreSupport = true, basePackages = "hello.repository.solr")
// @Profile("prod")
public class SolrConfiguration {

	// @Resource
	private Environment environment;

	// @Bean(name = "solrProductTemplate")
	public SolrTemplate solrDiscussionsTemplate() throws Exception {
		final HttpSolrServer httpSolrServer = new HttpSolrServer(environment.getRequiredProperty("spring.data.solr.host"));
		return new SolrTemplate(httpSolrServer, "product");
	}

	// @Bean(name = "solrCustomerTemplate")
	public SolrTemplate solrProductsTemplate() throws Exception {
		final HttpSolrServer httpSolrServer = new HttpSolrServer(environment.getRequiredProperty("spring.data.solr.host"));
		return new SolrTemplate(httpSolrServer, "customer");
	}

	// @Bean
	public SolrServer solrServer() {
		return new HttpSolrServer(environment.getRequiredProperty("spring.data.solr.host"));
	}
}
