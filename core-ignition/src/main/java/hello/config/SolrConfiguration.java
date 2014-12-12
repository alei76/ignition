package hello.config;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.repository.config.EnableSolrRepositories;

@Configuration
@ComponentScan(basePackages = { "hello.solr" })
@EnableSolrRepositories(multicoreSupport = true, basePackages = "hello.repository.solr")
// @Profile("prod")
public class SolrConfiguration {

	@Resource
	private Environment environment;

	@Bean
	public SolrServer solrServer() {
		return new HttpSolrServer(
				environment.getRequiredProperty("spring.data.solr.host"));
	}

	@Bean(name = "solrCustomerTemplate")
	public SolrTemplate solrProductsTemplate() throws Exception {
		HttpSolrServer httpSolrServer = new HttpSolrServer(
				environment.getRequiredProperty("spring.data.solr.host"));
		return new SolrTemplate(httpSolrServer, "customer");
	}

	@Bean(name = "solrProductTemplate")
	public SolrTemplate solrDiscussionsTemplate() throws Exception {
		HttpSolrServer httpSolrServer = new HttpSolrServer(
				environment.getRequiredProperty("spring.data.solr.host"));
		return new SolrTemplate(httpSolrServer, "product");
	}
}
