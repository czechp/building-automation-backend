package app.web.pczportfolio.pczbuildingautomation.utilities.messaging;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class MessagingMessageSender {
    private final RabbitTemplate rabbitTemplate;

    void sendMessageToQueue(DeviceChannel deviceChannel, DeviceChannelMsg deviceChannelMsg) throws JsonProcessingException {
        MessagingNameSet names = new MessagingNameSet(deviceChannel);
        rabbitTemplate.convertAndSend(
                names.getExchangeName(),
                names.getRoutingKeyName(),
                deviceChannelMsg
        );
    }
}
