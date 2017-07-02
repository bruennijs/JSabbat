package sabbat.apigateway.location.command;

import com.sun.javafx.binding.StringFormatter;

/**
 * Created by bruenni on 04.08.16.
 */
public abstract class ActivityCommandFactory implements IActivityCommandFactory {

    private static final long REPLY_TIMEOUT = 5000;

    /**
     * Spring lookup method
     *
     * @param activity_id
     * @param title
     * @param points
     * @return
     */
    protected abstract ICommand createStartActivityCommand(String activity_id, String title, String points);

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
            return new FaultTolerantCommandDecorator(createStartActivityCommand(activity_id, title, points), REPLY_TIMEOUT);
            //return createStartActivityCommand(UUID.randomUUID().toString(), title, points);
        }
        else if (requestType.equals("stop_activity"))
        {
            return new FaultTolerantCommandDecorator(createStopActivityCommand(activity_id), REPLY_TIMEOUT);
        }
        else if (requestType.equals("update_activity")) {
            return createUpdateActivityCommand(activity_id, points);
        }

        throw new Exception(StringFormatter.format("requesttype not supported [type=%1s]", requestType).getValue());
    }
}
