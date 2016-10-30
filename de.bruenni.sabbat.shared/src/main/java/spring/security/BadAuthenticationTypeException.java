package spring.security;

import org.springframework.security.core.AuthenticationException;

/**
 * Created by bruenni on 30.10.16.
 */
public class BadAuthenticationTypeException extends AuthenticationException {
    public BadAuthenticationTypeException(String msg) {
        super(msg);
    }
}
