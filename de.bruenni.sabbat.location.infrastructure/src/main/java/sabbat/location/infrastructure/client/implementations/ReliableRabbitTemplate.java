package sabbat.location.infrastructure.client.implementations;

import org.slf4j.Logger;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.AmqpMessageReturnedException;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import rx.subjects.ReplaySubject;
import rx.subjects.Subject;

import java.util.HashMap;

/**
 * Created by bruenni on 13.10.16.
 */
public class ReliableRabbitTemplate extends RabbitTemplate {

    final Logger sl4jLogger = org.slf4j.LoggerFactory.getLogger(ReliableRabbitTemplate.class);

    //private HashMap<String, ReplaySubject<Void>> subjectMap = new HashMap<>();
    private Subject<Void, Void> responseSubject = null;

    /**
     * Constructor
     * @param connectionFactory
     */
    public ReliableRabbitTemplate(ConnectionFactory connectionFactory) {
        super(connectionFactory);

        //// enable message returns: to ensure reliable queue delivery
        this.setMandatory(true);
        this.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            sl4jLogger.debug(String.format("update return %1s,%2s", replyCode, replyText));
            this.responseSubject.onError(new AmqpMessageReturnedException("Message returned", message, replyCode, replyText, exchange, routingKey));
        });


        this.setConfirmCallback((correlationData, ack, cause) ->
        {
            sl4jLogger.debug(String.format("update confirmation [correlationId=%1s, %2s, cause=%3s]", correlationData != null ? correlationData.getId() : "null", ack, cause));

            try
            {
                this.responseSubject.onNext(null);
            }
            finally {
                this.responseSubject.onCompleted();
                this.responseSubject = null;
            }
        });
    }

    /**
     * Sends reliable and throws exception when basic.return and message could not be delivered
     * to a queue.
     * @param message
     * @return
     * @throws AmqpException
     */
    public rx.Observable<Void> sendReliable(Message message) throws AmqpException {

        sl4jLogger.debug("CID [" + message.getMessageProperties().getCorrelationIdString() + "]");

        if ( this.responseSubject != null)
        {
            throw new IllegalArgumentException("Pending request. this template can only run one request at a time.");
        }

        this.responseSubject = ReplaySubject.create();

        try {
            super.send(message);

            return this.responseSubject;
        }
        catch (Exception exception)
        {
            this.responseSubject = null;
            throw exception;
        }
    }
}
