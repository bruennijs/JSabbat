package sabbat.location.infrastructure.service.adapter;

import com.rabbitmq.client.Channel;
import identity.IAuthenticationService;
import identity.UserRef;
import infrastructure.identity.Token;
import infrastructure.util.Tuple2;
import jdk.nashorn.internal.parser.DateParser;
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
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.stereotype.Component;
import sabbat.location.core.application.service.ActivityApplicationService;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.infrastructure.DtoToCommand.ActivityCreateRequestDtoConverter;
import sabbat.location.infrastructure.DtoToCommand.ActivityUpdateEventDtoConverter;
import sabbat.location.infrastructure.client.dto.ActivityCreateRequestDto;
import sabbat.location.infrastructure.client.dto.ActivityCreatedResponseDto;
import sabbat.location.infrastructure.client.dto.ActivityUpdateEventDto;

import java.io.IOException;

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
    public ActivityApplicationService applicationService;

    @Autowired
    @Qualifier("verifyingAuthenticationService")
    public IAuthenticationService authenticationService;

    @Autowired
    @Qualifier("locationJsonDtoParser")
    private infrastructure.parser.IDtoParser dtoParser;

    private ActivityCreateRequestDtoConverter dtoCreateConverter = new ActivityCreateRequestDtoConverter();
    private ActivityUpdateEventDtoConverter dtoUpdateConverter = new ActivityUpdateEventDtoConverter();

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


                ActivityCreateRequestDto dto = dtoParser.parse(message.getBody(), ActivityCreateRequestDto.class);

                // veryify token
                UserRef userRef = authenticationService.verify(Token.valueOf(dto.getIdentityToken()));

                ActivityCreateCommand command = dtoCreateConverter.convert(new Tuple2<>(userRef, dto));

                Activity activity = this.applicationService.start(command);

                ack(message, channel);

                //Observable.from(activityStartFuture)
                ActivityCreatedResponseDto dtoResponse = new ActivityCreatedResponseDto(activity.getUuid());

                MessageProperties msgProps = MessagePropertiesBuilder.newInstance()
                        .setCorrelationId(correlationId)
                        .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                        .build();

                Message responseMessage = MessageBuilder.withBody(dtoParser.serialize(dtoResponse).getBytes())
                        .andProperties(msgProps)
                        .build();

                logger.debug("<-- response [" + responseMessage.toString() + "cid=" + correlationId + "]");

                return responseMessage;
            }
        }
        catch (Exception exception)
        {
            nack(message, channel, exception);
        }

        logger.error("Message could not be handled -> return message == null");
        return null;
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
            // veryify token
            UserRef userRef = authenticationService.verify(Token.valueOf(dto.getIdentityToken()));

            ActivityUpdateCommand command = this.dtoUpdateConverter.convert(new Tuple2<>(userRef, dto));

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
}
