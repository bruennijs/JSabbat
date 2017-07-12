package spring.security;

import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * Created by bruenni on 11.07.17.
 */
public class UserExtended extends org.springframework.security.core.userdetails.User {

    private String id;

    public UserExtended(String id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        this.id = id;
    }

    public UserExtended(String id, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, enabled, accountNonExpired, credentialsNonExpired, accountNonLocked, authorities);
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
