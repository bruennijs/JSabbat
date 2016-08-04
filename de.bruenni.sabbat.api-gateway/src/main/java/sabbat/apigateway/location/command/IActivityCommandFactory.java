package sabbat.apigateway.location.command;

import sabbat.location.infrastructure.client.dto.IActivityResponseDto;

/**
 * Created by bruenni on 04.08.16.
 */
public interface IActivityCommandFactory {
    /**
     * creates command to be executed.
     * @param requestType
     * @param title
     * @param points
     * @param source
     * @param activity_id
     * @return
     * @throws Exception
     */
    ICommand getCommand(String requestType, String title, String points, String source, String activity_id) throws Exception;
}
