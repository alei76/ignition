package hello.domain;

import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CustomerTest {

	@Test
	public void test() {
		final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		final String encode = bCryptPasswordEncoder.encode("james");
		System.out.println(encode);
	}

}
