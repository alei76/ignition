package hello.infrastructure.security;

import hello.infrastructure.Cast;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.AuthorityUtils;

public class BackendAdminUsernamePasswordAuthenticationProvider implements AuthenticationProvider {

	public static final String INVALID_BACKEND_ADMIN_CREDENTIALS = "Invalid Backend Admin Credentials";

	@Value("${backend.admin.password}")
	private String backendAdminPassword;

	@Value("${backend.admin.username}")
	private String backendAdminUsername;

	@Override
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {
		final String username = Cast.cast(authentication.getPrincipal());
		final String password = Cast.cast(authentication.getCredentials());

		if (credentialsMissing(username, password) || credentialsInvalid(username, password)) {
			throw new BadCredentialsException(INVALID_BACKEND_ADMIN_CREDENTIALS);
		}

		return new UsernamePasswordAuthenticationToken(username, null, AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_BACKEND_ADMIN"));
	}

	private boolean credentialsInvalid(final String username, final String password) {
		return !isBackendAdmin(username) || !password.equals(backendAdminPassword);
	}

	private boolean credentialsMissing(final String username, final String password) {
		return username == null || password == null;
	}

	private boolean isBackendAdmin(final String username) {
		return backendAdminUsername.equals(username);
	}

	@Override
	public boolean supports(final Class<?> authentication) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
}
