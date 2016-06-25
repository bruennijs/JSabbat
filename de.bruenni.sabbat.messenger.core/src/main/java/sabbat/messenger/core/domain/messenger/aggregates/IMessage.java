package sabbat.messenger.core.domain.messenger.aggregates;

import infrastructure.persistence.IEntity;
import sabbat.messenger.core.domain.messenger.ValueObjects.User;

import java.util.Date;
import java.util.UUID;

/**
 * Created by bruenni on 25.06.16.
 */
public interface IMessage extends IEntity<UUID> {
    Date getCreatedOn();

    Date getDeliveredOn();

    User getTo();

    User getFrom();

    MessageState getState();
}
