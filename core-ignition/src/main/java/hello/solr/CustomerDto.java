package hello.solr;

import lombok.Data;

import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.annotation.Id;

@Data
public class CustomerDto {

	private String id;

	private String firstName;
	
	private String lastName;
	
	private long version;
	

}