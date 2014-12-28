package hello.infrastructure.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.MessageDigestPasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;
import org.springframework.web.util.UrlPathHelper;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.common.base.Strings;

public class AuthenticationFilter extends GenericFilterBean {

	private final static Logger logger = LoggerFactory.getLogger(AuthenticationFilter.class);
	public static final String TOKEN_SESSION_KEY = "token";
	public static final String USER_SESSION_KEY = "user";
	@Value("${security.authenticate.url}")
	private String authenticateUrl;

	private final AuthenticationManager authenticationManager;

	private final TokenService tokenService;

	public AuthenticationFilter(final AuthenticationManager authenticationManager, final TokenService tokenService) {
		this.authenticationManager = authenticationManager;
		this.tokenService = tokenService;
	}

	private void addSessionContextToLogging() {
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String tokenValue = "EMPTY";
		if (authentication != null && !Strings.isNullOrEmpty(authentication.getDetails().toString())) {
			final MessageDigestPasswordEncoder encoder = new MessageDigestPasswordEncoder("SHA-1");
			tokenValue = encoder.encodePassword(authentication.getDetails().toString(), "not_so_random_salt");
		}
		MDC.put(TOKEN_SESSION_KEY, tokenValue);

		String userValue = "EMPTY";
		if (authentication != null && !Strings.isNullOrEmpty(authentication.getPrincipal().toString())) {
			userValue = authentication.getPrincipal().toString();
		}
		MDC.put(USER_SESSION_KEY, userValue);
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
		final Optional<String> token = Optional.fromNullable(httpRequest.getHeader("X-Auth-Token"));

		final String resourcePath = new UrlPathHelper().getPathWithinApplication(httpRequest);

		try {
			if (postToAuthenticate(httpRequest, resourcePath)) {
				logger.debug("Trying to authenticate user {} by X-Auth-Username method", username);
				processUsernamePasswordAuthentication(httpResponse, username, password);
				return;
			}

			if (token.isPresent()) {
				logger.debug("Trying to authenticate user by X-Auth-Token method. Token: {}", token);
				processTokenAuthentication(token);
			}

			logger.debug("AuthenticationFilter is passing request down the filter chain");
			addSessionContextToLogging();
			chain.doFilter(request, response);
		} catch (final InternalAuthenticationServiceException internalAuthenticationServiceException) {
			SecurityContextHolder.clearContext();
			logger.error("Internal authentication service exception", internalAuthenticationServiceException);
			httpResponse.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		} catch (final AuthenticationException authenticationException) {
			SecurityContextHolder.clearContext();
			httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED, authenticationException.getMessage());
		} finally {
			MDC.remove(TOKEN_SESSION_KEY);
			MDC.remove(USER_SESSION_KEY);
		}
	}

	private boolean postToAuthenticate(final HttpServletRequest httpRequest, final String resourcePath) {
		return authenticateUrl.equalsIgnoreCase(resourcePath) && httpRequest.getMethod().equals("POST");
	}

	private void processTokenAuthentication(final Optional<String> token) {
		final Authentication resultOfAuthentication = tryToAuthenticateWithToken(token);
		SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
	}

	private void processUsernamePasswordAuthentication(final HttpServletResponse httpResponse, final Optional<String> username, final Optional<String> password)
			throws IOException {
		final Authentication resultOfAuthentication = tryToAuthenticateWithUsernameAndPassword(username, password);
		SecurityContextHolder.getContext().setAuthentication(resultOfAuthentication);
		httpResponse.setStatus(HttpServletResponse.SC_OK);
		final TokenResponse tokenResponse = new TokenResponse(resultOfAuthentication.getDetails().toString());
		final String tokenJsonResponse = new ObjectMapper().writeValueAsString(tokenResponse);
		httpResponse.addHeader("Content-Type", "application/json");
		httpResponse.getWriter().print(tokenJsonResponse);
	}

	private Authentication tryToAuthenticate(final Authentication requestAuthentication) {
		final Authentication responseAuthentication = authenticationManager.authenticate(requestAuthentication);
		if (responseAuthentication == null || !responseAuthentication.isAuthenticated()) {
			throw new InternalAuthenticationServiceException("Unable to authenticate Domain User for provided credentials");
		}
		logger.debug("User successfully authenticated");
		final AuthenticationWithToken resultOfAuthentication = new AuthenticationWithToken(requestAuthentication.getPrincipal(),
				requestAuthentication.getCredentials(), responseAuthentication.getAuthorities());
		final String newToken = tokenService.generateNewToken();
		resultOfAuthentication.setToken(newToken);
		tokenService.store(newToken, resultOfAuthentication);
		return resultOfAuthentication;
	}

	private Authentication tryToAuthenticateWithToken(final Optional<String> token) {
		final PreAuthenticatedAuthenticationToken requestAuthentication = new PreAuthenticatedAuthenticationToken(token, null);
		return tryToAuthenticate(requestAuthentication);
	}

	private Authentication tryToAuthenticateWithUsernameAndPassword(final Optional<String> username, final Optional<String> password) {
		final UsernamePasswordAuthenticationToken requestAuthentication = new UsernamePasswordAuthenticationToken(username.get(), password.get());
		return tryToAuthenticate(requestAuthentication);
	}
}
