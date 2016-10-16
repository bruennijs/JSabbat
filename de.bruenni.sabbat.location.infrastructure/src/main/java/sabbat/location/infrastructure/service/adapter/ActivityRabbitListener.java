package sabbat.location.infrastructure.service.adapter;

import com.rabbitmq.client.Channel;
import infrastructure.identity.Token;
import infrastructure.util.IterableUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import sabbat.location.core.application.service.IActivityApplicationService;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.core.domain.model.ActivityCoordinate;
import sabbat.location.core.domain.model.ActivityCoordinatePrimaryKey;
import sabbat.location.infrastructure.client.dto.ActivityCreateRequestDto;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;
import sabbat.location.infrastructure.client.dto.ActivityUpdateEventDto;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Created by bruenni on 07.09.16.
 */
@Component
public class ActivityRabbitListener {

    private Logger logger = LoggerFactory.getLogger(ActivityRabbitListener.class);

    @Value("${location.activity.routingkey.command.start}")
    public String ActivityStartRoutingKey;

    @Value("${location.activity.routingkey.command.stop}")
    public String ActivityStopRoutingKey;

    @Autowired
    public IActivityApplicationService applicationService;

    @Autowired
    @Qualifier("locationJsonDtoParser")
    private infrastructure.parser.IDtoParser dtoParser;

    @RabbitListener(queues = "${location.activity.queue.command}",
                    containerFactory = "rabbitListenerContainerFactory")
    //@Header("correlationId") String correlationId
    public Message onCommandMessage(Message message,
                             @Header(AmqpHeaders.RECEIVED_ROUTING_KEY) String routingKey,
                             Channel channel,
                             @Header(AmqpHeaders.CORRELATION_ID) byte[] correlationId) throws Exception {
        logger.debug("--> command [" + message.toString() + "cid=" + correlationId + ",routingkey=" + routingKey + "]");

        try
        {
            if (routingKey.equals(ActivityStartRoutingKey)) {


                ActivityCreateRequestDto dtoRequest = dtoParser.parse(message.getBody(), ActivityCreateRequestDto.class);

                ActivityCreateCommand command = new ActivityCreateCommand(Token.valueOf(dtoRequest.getIdentityToken()),
                        dtoRequest.getId(),
                        dtoRequest.getTitle());

                Activity activity = this.applicationService.start(command);

                ack(message, channel);

                //Observable.from(activityStartFuture)
                ActivityCreatedResponseDto dtoResponse = new ActivityCreatedResponseDto(activity.getKey().getId().toString());

                MessageProperties msgProps = MessagePropertiesBuilder.newInstance()
                        .setCorrelationId(correlationId)
                        .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                        .build();

                Message responseMessage = MessageBuilder.withBody(dtoParser.serialize(dtoResponse).getBytes())
                        .andProperties(msgProps)
                        .build();

                logger.debug("<-- response [" + responseMessage.toString() + "cid=" + correlationId + "]");
            }
        }
        catch (Exception exception)
        {
            nack(message, channel, exception);
        }

        throw new Exception("not implemented");
    }

    @RabbitListener(queues = "${location.activity.queue.tracking}",
                    containerFactory = "rabbitListenerContainerFactory")
    public void onTrackingMessage(Message message,
                                  Channel channel) throws Exception {
        ActivityUpdateEventDto dto = dtoParser.parse(message.getBody(), ActivityUpdateEventDto.class);
        logger.debug("--> tracking [" + message.toString() + "]");
        logger.debug("--> DTO      [" + dto.toString() + "]");

        try
        {
            ActivityUpdateCommand command = new ActivityUpdateCommand(toActivityCoordinates(dto), null, null);

            this.applicationService.update(command);

            ack(message, channel);
        }
        catch (Exception exception) {
            nack(message, channel, exception);
        }
    }

    private void nack(Message message, Channel channel, Exception exception) throws IOException {
        logger.error("nack AMQP message without requeue", exception);
        channel.basicNack(message.getMessageProperties().getDeliveryTag(), false, false);
    }

    private void ack(Message message, Channel channel) throws IOException {
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    private List<ActivityCoordinate> toActivityCoordinates(ActivityUpdateEventDto dto) {
        return IterableUtils.stream(dto.getTimeSeries()).map(ts -> {

            ActivityCoordinatePrimaryKey pKey = new ActivityCoordinatePrimaryKey(dto.getIdentityToken(), dto.getId(), ts.getTimestamp());

            return new ActivityCoordinate(pKey, ts.getLatitude(), ts.getLongitude());
        }).collect(Collectors.toList());
    }
}
