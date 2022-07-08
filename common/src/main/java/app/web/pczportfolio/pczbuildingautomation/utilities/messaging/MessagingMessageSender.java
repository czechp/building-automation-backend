package app.web.pczportfolio.pczbuildingautomation.utilities.messaging;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class MessagingMessageSender {
    private final RabbitTemplate rabbitTemplate;

    void sendMessageToQueue(DeviceChannel deviceChannel, String message) {
        MessagingNameSet names = new MessagingNameSet(deviceChannel);
        rabbitTemplate.convertAndSend(
                names.getExchangeName(),
                names.getRoutingKeyName(),
                message
        );
    }
}
