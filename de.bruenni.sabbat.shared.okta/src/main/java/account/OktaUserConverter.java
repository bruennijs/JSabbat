package account;

import com.okta.sdk.resource.group.*;
import com.okta.sdk.resource.user.*;
import com.okta.sdk.resource.user.User;
import identity.GroupRef;
import identity.UserRef;
import org.springframework.core.convert.converter.Converter;

import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * Created by bruenni on 17.06.17.
 */
class OktaUserConverter<TUser extends UserRef> implements Converter<com.okta.sdk.resource.user.User, TUser> {

	private BiFunction<User, List<GroupRef>, ? extends TUser> converterFunction;

	public OktaUserConverter(BiFunction<com.okta.sdk.resource.user.User, List<GroupRef>, ? extends TUser> converterFunction) {
		this.converterFunction = converterFunction;
	}

	@Override
	public TUser convert(com.okta.sdk.resource.user.User user) {

		Stream<com.okta.sdk.resource.group.Group> groupStream = StreamSupport.stream(user.listGroups().spliterator(), false);

		return converterFunction.apply(user, groupStream.map(OktaUserConverter::toGroupRef).collect(Collectors.toList()));
	}

	/**
	 * Converts to GroupRef instance.
	 * @param group
	 * @return
	 */
	private static GroupRef toGroupRef(com.okta.sdk.resource.group.Group group) {
		return new GroupRef(group.getId());
	}
}
