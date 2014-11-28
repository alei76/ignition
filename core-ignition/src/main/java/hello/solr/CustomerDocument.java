package hello.solr;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;

@Data 
@NoArgsConstructor
public class CustomerDocument {

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
		private CustomerDocument build;

		public Builder(String id) {
			build = new CustomerDocument();
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
		
		public CustomerDocument build() {
			return build;
		}
	}
}