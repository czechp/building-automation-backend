package app.web.pczportfolio.pczbuildingautomation.utilities.messaging;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@AllArgsConstructor
class MessagingQueueCreator {
    private static final String DLX_HEADER = "x-dead-letter-exchange";
    private static final String TTL_HEADER = "x-message-ttl";
    //TODO: change this value to 60 000 == 1 min
    private static final int TTL_MILISECONDS_VALUE = 1_000;
    private final RabbitAdmin rabbitAdmin;

    String createDeviceQueue(DeviceChannel deviceChannel) {
        final var names = new MessagingNameSet(deviceChannel);
        deleteQueueIfExists(names);
        final var queue = createQueue(names);
        return queue.getName();
    }

    private void deleteQueueIfExists(MessagingNameSet messagingNameSet) {
        rabbitAdmin.deleteQueue(messagingNameSet.getQueueName());
    }

    private Queue createQueue(MessagingNameSet names) {
        final var queue = new Queue(names.getQueueName(), true, false, false, createQueueArguments(names));
        rabbitAdmin.declareQueue(queue);
        final var exchange = new DirectExchange(names.getExchangeName());
        final var binding = BindingBuilder.bind(queue)
                .to(exchange)
                .with(names.getRoutingKeyName());
        rabbitAdmin.declareBinding(binding);
        return queue;
    }

    private Map<String, Object> createQueueArguments(MessagingNameSet names) {
        final var arguments = new HashMap<String, Object>();
        arguments.put(DLX_HEADER, names.getExchangeDlxName());
        arguments.put(TTL_HEADER, TTL_MILISECONDS_VALUE);
        return arguments;
    }


}
