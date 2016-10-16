package sabbat.location.core.builder;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.mockito.AdditionalAnswers;
import org.mockito.stubbing.OngoingStubbing;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.persistence.activity.IActivityRepository;

/**
 * Created by bruenni on 01.10.16.
 */
public class ActivityRepositoryBuilder {
    public IActivityRepository buildmocked() {
        IActivityRepository mockedObject = mock(IActivityRepository.class);
        when(mockedObject.save(any(Activity.class))).then(AdditionalAnswers.returnsFirstArg());
        return mockedObject;
    }
}
