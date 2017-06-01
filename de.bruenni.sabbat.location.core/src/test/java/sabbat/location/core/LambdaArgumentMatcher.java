package sabbat.location.core;

import org.mockito.ArgumentMatcher;

import java.util.function.Predicate;

/**
 * Created by bruenni on 01.06.17.
 */
public class LambdaArgumentMatcher<T> implements ArgumentMatcher<T> {
	private Predicate<? super T> callback;

	public LambdaArgumentMatcher(Predicate<? super T> callback) {
		this.callback = callback;
	}

	@Override
	public boolean matches(Object argument) {
		return callback.test((T)argument);
	}
}
