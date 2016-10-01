package sabbat.location.core.builder;

import static org.mockito.Mockito.mock;

import sabbat.location.core.persistence.activity.IActivityRepository;

/**
 * Created by bruenni on 01.10.16.
 */
public class ActivityRepositoryBuilder {
    public IActivityRepository buildmocked() {
        return mock(IActivityRepository.class);
    }
}
