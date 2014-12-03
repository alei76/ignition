package hello.domain;

import lombok.Data;

@Data
public class CustomerDto {

	private String id;

	private String firstName;

	private String lastName;

	private long version;

}