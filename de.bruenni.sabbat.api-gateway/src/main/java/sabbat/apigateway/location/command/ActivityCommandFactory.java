package sabbat.apigateway.location.command;

import com.sun.javafx.binding.StringFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import sabbat.location.infrastructure.client.dto.IActivityResponseDto;

import java.util.Date;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bruenni on 04.08.16.
 */
public abstract class ActivityCommandFactory implements IActivityCommandFactory {

    /**
     * Spring lookup method
     * @param title
     * @param points
     * @return
     */
    protected abstract ICommand createStartActivityCommand(String id, String title, String points);

    /**
     * Stops activity
     * @param id
     * @return
     */
    protected abstract ICommand createStopActivityCommand(String id);

    /**
     * Updates activity
     * @param id
     * @param points points A list of points indicating new location(s) to append to this activity. The list of points should be space delimited in blocks containing latitude, longitude, altitude, and time data. These blocks are themselves space delimited
     * @return
     */
    protected abstract ICommand createUpdateActivityCommand(String id, String points);


    /**
     * Creates activity command.
     * @param requestType
     * @param title
     * @param points
     * @param source
     * @param activity_id Can be null
     */
    @Override
    public ICommand getCommand(String requestType, String title, String points, String source, String activity_id) throws Exception {
        if (requestType.equals("start_activity")) {
            return createStartActivityCommand("4711", title, points);
            //return createStartActivityCommand(UUID.randomUUID().toString(), title, points);
        }
        else if (requestType.equals("stop_activity"))
        {
            return createStopActivityCommand(activity_id);
        }
        else if (requestType.equals("update_activity")) {
            return createUpdateActivityCommand(activity_id, points);
        }

        throw new Exception(StringFormatter.format("requesttype not supported [type=%1s]", requestType).getValue());
    }
}
