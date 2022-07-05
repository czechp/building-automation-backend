package app.web.pczportfolio.pczbuildingautomation.configuration.messaging;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class MessagingDirectExchangeCreator {
    private final RabbitAdmin rabbitAdmin;

    String createDirectExchange(MessagingChannel messagingChannel) {
        final var exchangeName = new MessagingNameSetCreator(messagingChannel).getExchangeName();
        rabbitAdmin.deleteExchange(exchangeName);
        rabbitAdmin.declareExchange(new DirectExchange(exchangeName));
        return exchangeName;
    }
}
