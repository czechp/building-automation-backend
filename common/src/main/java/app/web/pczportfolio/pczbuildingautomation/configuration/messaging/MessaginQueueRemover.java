package app.web.pczportfolio.pczbuildingautomation.configuration.messaging;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class MessaginQueueRemover {
    private final RabbitAdmin rabbitAdmin;

    void removeChannel(MessagingChannel messagingChannel) {
        final var nameOfChannel = new MessagingNameSetCreator(messagingChannel).getQueueName();
        rabbitAdmin.deleteQueue(nameOfChannel);
    }
}
