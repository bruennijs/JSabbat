package sabbat.location.infrastructure.service.adapter;

import com.rabbitmq.client.Channel;
import identity.IAuthenticationService;
import identity.UserRef;
import infrastructure.identity.AuthenticationFailedException;
import infrastructure.identity.Token;
import infrastructure.parser.ParserException;
import infrastructure.parser.SerializingException;
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
import sabbat.location.api.dto.*;
import sabbat.location.core.application.service.ActivityApplicationService;
import sabbat.location.core.application.service.command.ActivityCreateCommand;
import sabbat.location.core.application.service.command.ActivityUpdateCommand;
import sabbat.location.core.domain.model.Activity;
import sabbat.location.infrastructure.DtoToCommand.ActivityCreateRequestDtoConverter;
import sabbat.location.infrastructure.DtoToCommand.ActivityUpdateEventDtoConverter;

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
    public Message onCommandMessage(Message message,
                                    @Header(value = "AuthorizationToken") String accessToken,
                                    //@Header(AmqpHeaders.CORRELATION_ID) String correlationId,
                             Channel channel) throws Exception {

        logger.debug("--> command [" + message.toString() + "properties=" + message.getMessageProperties().getCorrelationIdString() + "]");

        try
        {
            Message responseMessage = executeDto(message, message.getMessageProperties().getReceivedRoutingKey(), accessToken, channel, message.getMessageProperties().getCorrelationIdString());

            logger.debug("<-- response [" + responseMessage.toString() + "cid=" + message.getMessageProperties().getCorrelationIdString() + "]");

            return responseMessage;
        }
        catch (Exception exception)
        {
            nack(message, channel, exception);
        }

        logger.error("Message could not be handled -> return message == null");
        return null;
    }

    private Message executeDto(Message message, String routingKey, String accessToken, Channel channel, String correlationId) throws Exception {
        // veryify token
        UserRef userRef = authenticationService.verify(Token.valueOf(accessToken));

        if (routingKey.equals(ActivityStartRoutingKey)) {
            return executeStart(message, userRef, channel, correlationId);
        }else if (routingKey.equals(ActivityStopRoutingKey))
        {
            return executeStop(message, channel, correlationId);
        }

        throw new Exception("Message not supported");
    }

    /***
     * Handling of Start DTO
     * @param message
     * @param userRef
     * @param channel
     * @param correlationId
     * @return
     * @throws infrastructure.parser.ParserException
     * @throws AuthenticationFailedException
     * @throws SerializingException
     * @throws IOException
     */
    private Message executeStart(Message message, UserRef userRef, Channel channel, String correlationId) throws infrastructure.parser.ParserException, AuthenticationFailedException, SerializingException, IOException {
        ActivityCreateRequestDto dto = dtoParser.parse(message.getBody(), ActivityCreateRequestDto.class);

        ActivityCreateCommand command = dtoCreateConverter.convert(new Tuple2<>(userRef, dto));

        Activity activity = this.applicationService.start(command);

        ack(message, channel);

        //Observable.from(activityStartFuture)
        ActivityCreatedResponseDto dtoResponse = new ActivityCreatedResponseDto(activity.getUuid());

        Message responseMessage = serializeResponseDto(message, dtoResponse);

        return responseMessage;
    }

    private <T> Message serializeResponseDto(Message request, T dto) throws SerializingException {
        MessageProperties msgProps = MessagePropertiesBuilder.newInstance()
                .copyProperties(request.getMessageProperties())
                .setCorrelationId(request.getMessageProperties().getCorrelationId())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();

        return MessageBuilder.withBody(dtoParser.serialize(dto).getBytes())
                .andProperties(msgProps)
                .build();
    }

    /***
     * Handling of Stop DTO
     * @param message
     * @param channel
     * @param correlationId
     * @return
     * @throws Exception
     */
    private Message executeStop(Message message, Channel channel, String correlationId) throws Exception {
        ActivityStopRequestDto dto = dtoParser.parse(message.getBody(), ActivityStopRequestDto.class);

        this.applicationService.stop(dto.getId());

        ack(message, channel);

        ActivityStoppedResponseDto responseDto = new ActivityStoppedResponseDto(dto.getId());

        return serializeResponseDto(message, responseDto);
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

            // TBD: refactor and exclude into own listener
            //this.applicationService.insert(command);

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
