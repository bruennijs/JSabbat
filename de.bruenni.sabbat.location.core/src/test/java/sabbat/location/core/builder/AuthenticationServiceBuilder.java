package sabbat.location.core.builder;

import identity.IAuthenticationService;
import identity.UserRef;
import infrastructure.identity.AuthenticationFailedException;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by bruenni on 16.10.16.
 */
public class AuthenticationServiceBuilder {

    private UserRef userRef;

    public AuthenticationServiceBuilder withUserRef(UserRef value)
    {
        this.userRef = value;
        return this;
    }

    public identity.IAuthenticationService buildMocked() throws AuthenticationFailedException {
        IAuthenticationService mockedObject = mock(IAuthenticationService.class);
        when(mockedObject.verify(any())).thenReturn(this.userRef);
        return mockedObject;
    }
}
