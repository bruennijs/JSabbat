package identity;

import infrastructure.identity.Jwt;

import java.time.Instant;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 16.10.16.
 */
public class UserJwtBuilder {

    public static final String USERNAME = "username";
    private static final String GROUPS = "groups";
    private HashMap claims;
    private String userName;
    private List<String> groupNames;
    private Instant iat;
    private Instant exp;


    /**
     * Adds data.
     * @param userName
     * @param groupNames
     * @param iat
     * @param exp
     * @return
     */
    public UserJwtBuilder withData(String userName, List<String> groupNames, Instant iat, Instant exp) {
        this.userName = userName;
        this.groupNames = groupNames;
        this.iat = iat;
        this.exp = exp;
        this.claims = new HashMap<>();
        claims.put(USERNAME, userName);
        claims.put(GROUPS, groupNames.stream().collect(Collectors.joining(",")));
        return this;
    }

    public Jwt build() {
        return new Jwt(this.userName, Date.from(iat), Date.from(exp), claims);
    }

    public UserJwtBuilder fromJwt(Jwt value) {
        String groupsValue = (String)value.getClaims().get(GROUPS);
        this.userName = (String)value.getClaims().get(USERNAME);
        this.groupNames = Arrays.stream(groupsValue.split("\\ *,\\ *")).collect(Collectors.toList());
        this.iat = value.getIat().toInstant();
        this.exp = value.getExp().toInstant();
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public List<String> getGroupNames() {
        return groupNames;
    }
}
