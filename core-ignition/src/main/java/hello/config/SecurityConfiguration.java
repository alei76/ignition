package hello.config;

import hello.infrastructure.security.AuthenticationFilter;
import hello.infrastructure.security.BackendAdminUsernamePasswordAuthenticationProvider;
import hello.infrastructure.security.ManagementEndpointAuthenticationFilter;
import hello.infrastructure.security.TokenAuthenticationProvider;
import hello.infrastructure.security.TokenService;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

//@Configuration
// Security filters with filter chain are configured and applied
//@EnableWebMvcSecurity
// Allows to run Spring schedulers and periodically run some tasks. We use
// scheduler for evicting EhCache tokens.
//@EnableScheduling
// Allows AOP @PreAuthorize and some other annotations to be applied to methods
//@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	// @Value("${actuator.endpoints}")
	private String[] actuatorEndpoints;

	// @Value("${backend.admin.role}")
	private String backendAdminRole;

	// @Autowired
	private DataSource dataSource;

	// @Bean
	public AuthenticationProvider backendAdminUsernamePasswordAuthenticationProvider() {
		return new BackendAdminUsernamePasswordAuthenticationProvider();
	}

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		// secure actuator endpoints with properties based credentials
		auth.authenticationProvider(backendAdminUsernamePasswordAuthenticationProvider());
		// secure authentication (token request) with default authentication
		auth.authenticationProvider(tokenAuthenticationProvider());
		auth.jdbcAuthentication().dataSource(dataSource).passwordEncoder(new BCryptPasswordEncoder());
		auth.inMemoryAuthentication().withUser("user").password("user").roles("USER");
	}

	@Override
	protected void configure(final HttpSecurity http) throws Exception {
		// We don’t need CSRF
		http.csrf().disable();
		// We don’t need typical HTTP session
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		// authorize requests on Spring-Actuator endpoints to any principal that has role of backend administrator
				.and().authorizeRequests().antMatchers(actuatorEndpoints).hasRole(backendAdminRole).anyRequest().authenticated() //
				// require all other requests to be authenticated
				.and().anonymous().disable()
				// on authentication error return a 401
				.exceptionHandling().authenticationEntryPoint(unauthorizedEntryPoint());

		http.addFilterBefore(new AuthenticationFilter(authenticationManager(), tokenService()), BasicAuthenticationFilter.class).addFilterBefore(
				new ManagementEndpointAuthenticationFilter(authenticationManager()), BasicAuthenticationFilter.class);
	}

	// @Bean
	// public JdbcUserDetailsManager jdbcUserDetailsManager() {
	// final JdbcUserDetailsManager jdbcUserDetailsManager = new JdbcUserDetailsManager();
	// jdbcUserDetailsManager.setDataSource(dataSource);
	// return jdbcUserDetailsManager;
	// }
	//
	// @Bean
	// public JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> jdbcUserDetailsManagerConfigurer() {
	// final JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder> jdbcUserDetailsManagerConfigurer = new
	// JdbcUserDetailsManagerConfigurer<AuthenticationManagerBuilder>(
	// jdbcUserDetailsManager());
	// jdbcUserDetailsManagerConfigurer.passwordEncoder(new BCryptPasswordEncoder()).withDefaultSchema();
	// return jdbcUserDetailsManagerConfigurer;
	// }

	// @Bean
	public AuthenticationProvider tokenAuthenticationProvider() {
		return new TokenAuthenticationProvider(tokenService());
	}

	// @Bean
	public TokenService tokenService() {
		return new TokenService();
	}

	// @Bean
	public AuthenticationEntryPoint unauthorizedEntryPoint() {
		return new AuthenticationEntryPoint() {
			@Override
			public void commence(final HttpServletRequest request, final HttpServletResponse response, final AuthenticationException authException)
					throws IOException, ServletException {
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
		};
	}
}
