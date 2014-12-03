package hello.solr;

import lombok.Data;
import lombok.NoArgsConstructor;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.SolrDocument;

@Data 
@NoArgsConstructor
@SolrDocument(solrCoreName="product")
public class ProductSolr {

	@Id
	@Field
	private String id;

	@Field("sku")
	private String sku;
	
	@Field("description")
	private String description;

	public static Builder getBuilder(String id, String sku) {
		return new Builder(id,  sku);
	}

	public static class Builder {
		private ProductSolr build;

		public Builder(String id, String sku) {
			build = new ProductSolr();
			build.id = id;
			build.sku = sku;
		}

		public Builder firstName(String description) {
			build.description = description;
			return this;
		}
		
		public ProductSolr build() {
			return build;
		}
	}
}