package builder;

import sabbat.messenger.core.infrastructure.persistence.IMessageRepository;

import static org.mockito.Mockito.mock;

/**
 * Created by bruenni on 25.06.16.
 */
public class MessageRepositoryBuilder {
    public IMessageRepository BuildMocked() {
        return mock(IMessageRepository.class);
    }
}
