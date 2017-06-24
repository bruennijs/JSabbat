package sabbat.location.core.application.service;

import identity.UserRef;
import infrastructure.common.event.IEventHandler;
import infrastructure.identity.AuthenticationFailedException;
import infrastructure.identity.Token;

/**
 * Created by bruenni on 14.03.17.
 */
public interface GroupActivityApplicationService  {

	/**
	 * External applications like the CEP receiving and processing
	 * user activity data calculates metrics like distances, positions, velocities between
	 * group members and maybe non-group members that want to become a group member.
	 * @param command command containing parameters.
	 */
	void updateGroupActivity(GroupActivityUpdateCommand command) throws AuthenticationFailedException;


	/**
	 * Command data container.
	 */
	class GroupActivityUpdateCommand {

		private double distanceInMeters;
		private UserRef user1;
		private UserRef user2;

		public GroupActivityUpdateCommand(UserRef user1, UserRef user2) {
			this.user1 = user1;
			this.user2 = user2;
		}

		public double getDistanceInMeters() {
			return distanceInMeters;
		}

		public UserRef getUser1() {
			return user1;
		}

		public UserRef getUser2() {
			return user2;
		}
	}
}
