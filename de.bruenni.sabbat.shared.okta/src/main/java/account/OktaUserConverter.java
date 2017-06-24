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

	public static final String GROUP_ID_EVERYONE = "00gau004jw4PlFs1J0h7";

	private BiFunction<User, List<GroupRef>, ? extends TUser> converterFunction;

	public OktaUserConverter(BiFunction<com.okta.sdk.resource.user.User, List<GroupRef>, ? extends TUser> converterFunction) {
		this.converterFunction = converterFunction;
	}

	@Override
	public TUser convert(com.okta.sdk.resource.user.User user) {

		List<GroupRef> groupRefList = StreamSupport.stream(user.listGroups().spliterator(), false)
			.filter(oktaGroup -> !oktaGroup.getId().equals(GROUP_ID_EVERYONE))	// in okta is every user in group 'everyone' per default , cannot be changed
			.map(OktaUserConverter::toGroupRef)
			.collect(Collectors.toList());

		return converterFunction.apply(user, groupRefList);
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
