package app.web.pczportfolio.pczbuildingautomation.utilities.messaging;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class MessagingMessageSender {
    private final RabbitTemplate rabbitTemplate;

    void sendMessageToQueue(MessagingChannel messagingChannel, String message) {
        MessagingNameSet names = new MessagingNameSet(messagingChannel);
        rabbitTemplate.convertAndSend(
                names.getExchangeName(),
                names.getRoutingKeyName(),
                message
        );
    }
}
