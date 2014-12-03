package hello.solr;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@Data 
@NoArgsConstructor
@SolrDocument(solrCoreName="customer")
public class CustomerSolr {

	@Id
	@Field
	private String id;

	@Field("first_name")
	private String firstName;
	
	@Field("last_name")
	private String lastName;


	public static Builder getBuilder(String id) {
		return new Builder(id);
	}

	public static class Builder {
		private CustomerSolr build;

		public Builder(String id) {
			build = new CustomerSolr();
			build.id = id;
		}

		public Builder firstName(String firstName) {
			build.firstName = firstName;
			return this;
		}
		public Builder lastName(String lastName) {
			build.lastName= lastName;
			return this;
		}
		
		public CustomerSolr build() {
			return build;
		}
	}
}