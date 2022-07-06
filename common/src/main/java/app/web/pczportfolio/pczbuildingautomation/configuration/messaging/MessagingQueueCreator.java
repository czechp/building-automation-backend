package app.web.pczportfolio.pczbuildingautomation.configuration.messaging;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class MessagingQueueCreator {
    private final RabbitAdmin rabbitAdmin;

    String createDeviceQueue(MessagingChannel messagingChannel) {
        final var names = new MessagingNameSet(messagingChannel);
        final var queue = new Queue(names.getQueueName());
        rabbitAdmin.declareQueue(queue);

        final var exchange = new DirectExchange(names.getExchangeName());

        final var binding = BindingBuilder.bind(queue)
                .to(exchange)
                .with(names.getRoutingKeyName());

        rabbitAdmin.declareBinding(binding);

        return queue.getName();
    }
}
