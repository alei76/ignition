package hello.infrastructure.security;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;

public class AuthenticationWithToken extends PreAuthenticatedAuthenticationToken {
	private static final long serialVersionUID = 1L;

	public AuthenticationWithToken(final Object aPrincipal, final Object aCredentials) {
		super(aPrincipal, aCredentials);
	}

	public AuthenticationWithToken(final Object aPrincipal, final Object aCredentials, final Collection<? extends GrantedAuthority> anAuthorities) {
		super(aPrincipal, aCredentials, anAuthorities);
	}

	public String getToken() {
		return (String) getDetails();
	}

	public void setToken(final String token) {
		setDetails(token);
	}
}
