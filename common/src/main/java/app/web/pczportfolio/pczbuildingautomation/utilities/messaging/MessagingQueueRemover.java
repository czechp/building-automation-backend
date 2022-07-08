package app.web.pczportfolio.pczbuildingautomation.utilities.messaging;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class MessagingQueueRemover {
    private final RabbitAdmin rabbitAdmin;

    void removeChannel(DeviceChannel deviceChannel) {
        final var nameOfChannel = new MessagingNameSet(deviceChannel).getQueueName();
        rabbitAdmin.deleteQueue(nameOfChannel);
    }
}
