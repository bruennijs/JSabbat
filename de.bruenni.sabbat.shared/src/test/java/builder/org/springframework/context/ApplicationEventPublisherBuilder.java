package builder.org.springframework.context;

import org.springframework.context.ApplicationEventPublisher;

import static org.mockito.Mockito.mock;

/**
 * Created by bruenni on 01.06.17.
 */
public class ApplicationEventPublisherBuilder {
	/**
	 *
	 * @return
	 */
	public ApplicationEventPublisher buildMocked()
	{
		return mock(ApplicationEventPublisher.class);
		//org.mockito.Mockito.when(mock.publishEvent(Matchers.any()));
	}
}
