package sabbat.messenger.core.application.services;

/**
 * Send command
 */
public class MessageSendCommand {
    private final String fromUserName;
    private final String toUserName;
    private final String content;

    public MessageSendCommand(String fromUserName, String toUserName, String content) {
        this.fromUserName = fromUserName;
        this.toUserName = toUserName;
        this.content = content;
    }

    public String getToUserName() {
        return toUserName;
    }

    public String getContent() {
        return content;
    }

    public String getFromUserName() {
        return fromUserName;
    }
}
