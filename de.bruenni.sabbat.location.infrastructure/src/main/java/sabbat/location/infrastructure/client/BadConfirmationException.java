package sabbat.location.infrastructure.client;

import sabbat.location.infrastructure.client.dto.ActivityUpdateEventDto;

/**
 * Created by bruenni on 12.10.16.
 */
public class BadConfirmationException extends Exception {
    public BadConfirmationException(String cause) {
        super(String.format("Bad confirmation of transport.[%1s]", cause));
    }
}
