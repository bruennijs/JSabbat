package sabbat.messenger.core.domain.messenger.aggregates;

/**
 * State each message can be in.
 */
public enum MessageState
{
    /**
     * Initial state.
     */
    New,
    /**
     * Send to delivery service and pending for state
     * delivered or failed to be sent
     */
    Pending,

    /**
     * Final state
     */
    Delivered,

    /**
     * Failed could not be sent
     */
    Failed
}
