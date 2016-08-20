package sabbat.apigateway.location.command;

import com.sun.javafx.binding.StringFormatter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import sabbat.location.infrastructure.client.dto.IActivityResponseDto;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by bruenni on 04.08.16.
 */
public abstract class ActivityCommandFactory implements IActivityCommandFactory {

    public static final String START_ACTIVITY_COMMAND_BEAN_NAME = "StartActivityCommand";

    @Autowired
    public ApplicationContext applicationContext;

    /**
     * Spring lookup method
     * @param title
     * @param points
     * @return
     */
    protected abstract StartActivityCommand createStartActivityCommand(String id, String title, String points);


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
            return createStartActivityCommand(UUID.randomUUID().toString(), title, points);
        }

        throw new Exception(StringFormatter.format("requesttype not supported [type=%1s]", requestType).getValue());
    }
}
