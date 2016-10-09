package sabbat.location.infrastructure.client.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by bruenni on 08.10.16.
 */
public abstract class ActivityRequestDtoBase extends ActivityDtoBase {
    @JsonProperty("token")
    private String identityToken;

    public ActivityRequestDtoBase() {
    }

    /**
     * Constructor
     * @param id
     * @param identityToken
     */
    public ActivityRequestDtoBase(String id, String identityToken) {
        super(id);
        this.identityToken = identityToken;
    }

    public String getIdentityToken() {
        return identityToken;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;

        ActivityRequestDtoBase that = (ActivityRequestDtoBase) o;

        return identityToken != null ? identityToken.equals(that.identityToken) : that.identityToken == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (identityToken != null ? identityToken.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ActivityRequestDtoBase{" +
                "identityToken='" + identityToken + '\'' +
                "} " + super.toString();
    }
}
