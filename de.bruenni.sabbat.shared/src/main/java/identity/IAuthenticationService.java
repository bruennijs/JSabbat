package identity;

import infrastructure.identity.AuthenticationFailedException;
import infrastructure.identity.Token;

/**
 * Created by bruenni on 15.10.16.
 */
public interface IAuthenticationService {
    /**
     * Validates token and receives reference to associated user.
     * @param token
     * @return
     */
    UserRef verify(Token token) throws AuthenticationFailedException;

    /***
     * Creates a singlesign on token to pass to multiple microservices
     * @param userName user name
     * @param password password
     * @return
     */
    Token authenticate(String userName, String password);
}
