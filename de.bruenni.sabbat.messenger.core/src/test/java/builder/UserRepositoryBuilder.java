package builder;

import sabbat.messenger.core.domain.identity.aggregates.User;
import sabbat.messenger.core.infrastructure.persistence.IUserRepository;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by bruenni on 25.06.16.
 */
public class UserRepositoryBuilder {

    private IUserRepository mock = mock(IUserRepository.class);

    public IUserRepository BuildMocked() {
        return mock;
    }

    public UserRepositoryBuilder WithFindByUserName(String userFromName, User returnUser) {
        when(mock.findByUserName(userFromName)).thenReturn(returnUser);
        return this;
    }
}
