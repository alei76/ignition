package hello.config;

import hello.infrastructure.security.BackendAdminUsernamePasswordAuthenticationProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
// Security filters with filter chain are configured and applied
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Value("${actuator.endpoints}")
	private String[] actuatorEndpoints;

	@Value("${backend.admin.role}")
	private String backendAdminRole;

	@Bean
	public AuthenticationProvider backendAdminUsernamePasswordAuthenticationProvider() {
		return new BackendAdminUsernamePasswordAuthenticationProvider();
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		http.authorizeRequests().antMatchers(actuatorEndpoints).hasRole(backendAdminRole). //
				antMatchers("/oops.html").permitAll(). //
				anyRequest().authenticated(). //
				and().formLogin().loginPage("/login").permitAll().defaultSuccessUrl("/hello.html"). //
				and().logout().permitAll();
	}

	@Autowired
	public void configureGlobal(final AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(backendAdminUsernamePasswordAuthenticationProvider());
		auth.inMemoryAuthentication().withUser("user").password("password").roles("USER");
	}
}
