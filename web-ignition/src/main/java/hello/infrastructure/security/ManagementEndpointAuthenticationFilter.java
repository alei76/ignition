package hello.infrastructure.security;

import java.io.IOException;
import java.util.Set;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import com.google.common.base.Optional;

public class ManagementEndpointAuthenticationFilter extends GenericFilterBean {

	private final static Logger logger = LoggerFactory.getLogger(ManagementEndpointAuthenticationFilter.class);
	private final AuthenticationManager authenticationManager;
	private final Set<String> managementEndpoints;

	public ManagementEndpointAuthenticationFilter(final AuthenticationManager authenticationManager, final Set<String> managementEndpoints) {
		this.authenticationManager = authenticationManager;
		this.managementEndpoints = managementEndpoints;
	}

	private HttpServletRequest asHttp(final ServletRequest request) {
		return (HttpServletRequest) request;
	}

	private HttpServletResponse asHttp(final ServletResponse response) {
		return (HttpServletResponse) response;
	}

	@Override
	public void doFilter(final ServletRequest request, final ServletResponse response, final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest httpRequest = asHttp(request);
		final HttpServletResponse httpResponse = asHttp(response);

		final Optional<String> username = Optional.fromNullable(httpRequest.getHeader("X-Auth-Username"));
		final Optional<String> password = Optional.fromNullable(httpRequest.getHeader("X-Auth-Password"));

		final String resourcePath = new UrlPathHelper().getPathWithinApplication(httpRequest);

		try {
			if (postToManagementEndpoints(resourcePath)) {
				logger.debug("Trying to authenticate user {} for management endpoint by X-Auth-Username method", username);
				processManagementEndpointUsernamePasswordAuthentication(username, password);
			}

			logger.debug("ManagementEndpointAuthenticationFilter is passing request down the filter chain");
			chain.doFilter(request, response);
		} catch (final AuthenticationException authenticationException) {
			SecurityContextHolder.clearContext();
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
		}
	}

	private boolean postToManagementEndpoints(final String resourcePath) {
		return managementEndpoints.contains(resourcePath);
	}

	private void processManagementEndpointUsernamePasswordAuthentication(final Optional<String> username, final Optional<String> password) throws IOException {
		final Authentication resultOfAuthentication = tryToAuthenticateWithUsernameAndPassword(username, password);
		SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
	}

	private Authentication tryToAuthenticate(final Authentication requestAuthentication) {
		final Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
		if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
			throw new InternalAuthenticationServiceException("Unable to authenticate Backend Admin for provided credentials");
		}
		logger.debug("Backend Admin successfully authenticated");
		return responseAuthentication;
	}

	private Authentication tryToAuthenticateWithUsernameAndPassword(final Optional<String> username, final Optional<String> password) {
		final BackendAdminUsernamePasswordAuthenticationToken requestAuthentication = new BackendAdminUsernamePasswordAuthenticationToken(username, password);
		return tryToAuthenticate(requestAuthentication);
	}
}
