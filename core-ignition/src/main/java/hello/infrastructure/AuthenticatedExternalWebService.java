package hello.infrastructure;

import org.springframework.security.core.GrantedAuthority;

import hello.infrastructure.externalwebservice.ExternalWebServiceStub;
import hello.infrastructure.security.AuthenticationWithToken;

import java.util.Collection;

public class AuthenticatedExternalWebService extends AuthenticationWithToken {

	private static final long serialVersionUID = 1L;
	private ExternalWebServiceStub externalWebService;

    public AuthenticatedExternalWebService(Object aPrincipal, Object aCredentials, Collection<? extends GrantedAuthority> anAuthorities) {
        super(aPrincipal, aCredentials, anAuthorities);
    }

    public void setExternalWebService(ExternalWebServiceStub externalWebService) {
        this.externalWebService = externalWebService;
    }

    public ExternalWebServiceStub getExternalWebService() {
        return externalWebService;
    }
}
