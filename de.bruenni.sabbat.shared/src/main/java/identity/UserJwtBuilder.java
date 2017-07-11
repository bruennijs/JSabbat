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

    public static final String USERID = "userid";
    private static final String GROUPS = "groups";
    private HashMap claims;
    private String userId;
    private List<String> groupNames;
    private Instant iat;
    private Instant exp;


    /**
     * Adds data.
     * @param userId
     * @param groupNames
     * @param iat
     * @param exp
     * @return
     */
    public UserJwtBuilder withData(String userId, List<String> groupNames, Instant iat, Instant exp) {
        this.userId = userId;
        this.groupNames = groupNames;
        this.iat = iat;
        this.exp = exp;
        this.claims = new HashMap<>();
        claims.put(USERID, userId);
        claims.put(GROUPS, groupNames.stream().collect(Collectors.joining(",")));
        return this;
    }

    public Jwt build() {
        return new Jwt(this.userId, Date.from(iat), Date.from(exp), claims);
    }

    public UserJwtBuilder fromJwt(Jwt value) {
        String groupsValue = (String)value.getClaims().get(GROUPS);
        this.userId = (String)value.getClaims().get(USERID);
        this.groupNames = Arrays.stream(groupsValue.split("\\ *,\\ *")).collect(Collectors.toList());
        this.iat = value.getIat().toInstant();
        this.exp = value.getExp().toInstant();
        return this;
    }

    public String getUserId() {
        return userId;
    }

    public List<String> getGroupNames() {
        return groupNames;
    }
}
