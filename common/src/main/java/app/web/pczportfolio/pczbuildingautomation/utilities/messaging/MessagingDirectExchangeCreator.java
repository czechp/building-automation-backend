package app.web.pczportfolio.pczbuildingautomation.utilities.messaging;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class MessagingDirectExchangeCreator {
    private final RabbitAdmin rabbitAdmin;

    String createDirectExchangeWithDlx(DeviceChannel deviceChannel) {
        final var names = new MessagingNameSet(deviceChannel);
        createDirectExchange(names);
        createFanoutDlxExchangeWithQueue(names);
        return names.getExchangeName();
    }


    private void createDirectExchange(MessagingNameSet names) {
        final var exchangeName = names.getExchangeName();
        rabbitAdmin.deleteExchange(exchangeName);
        rabbitAdmin.declareExchange(new DirectExchange(exchangeName));
    }

    private void createFanoutDlxExchangeWithQueue(MessagingNameSet names) {
        final var fanoutDlxExchange = new FanoutExchange(names.getExchangeDlxName());
        rabbitAdmin.declareExchange(fanoutDlxExchange);

        final var queueDlx = new Queue(names.getQueueDlxName());
        rabbitAdmin.deleteQueue(names.getQueueDlxName());
        rabbitAdmin.declareQueue(queueDlx);

        final var binding = BindingBuilder.bind(queueDlx)
                .to(fanoutDlxExchange);
        rabbitAdmin.declareBinding(binding);
    }

}
