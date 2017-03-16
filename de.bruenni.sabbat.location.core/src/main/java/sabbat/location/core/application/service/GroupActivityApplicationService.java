package sabbat.location.core.application.service;

import infrastructure.common.event.IEventHandler;
import infrastructure.identity.AuthenticationFailedException;
import infrastructure.identity.Token;

/**
 * Created by bruenni on 14.03.17.
 */
public interface GroupActivityApplicationService extends IEventHandler {

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

		private Token userToken1;

		private Token userToken2;

		private double distanceInMeters;

		public GroupActivityUpdateCommand(Token userToken1, Token userToken2) {
			this.userToken1 = userToken1;
			this.userToken2 = userToken2;
		}

		public Token getUserToken1() {
			return userToken1;
		}

		public Token getUserToken2() {
			return userToken2;
		}

		public double getDistanceInMeters() {
			return distanceInMeters;
		}
	}
}
