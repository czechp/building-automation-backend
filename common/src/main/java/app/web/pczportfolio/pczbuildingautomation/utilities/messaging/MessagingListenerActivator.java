package app.web.pczportfolio.pczbuildingautomation.utilities.messaging;

import lombok.AllArgsConstructor;
import org.springframework.amqp.rabbit.listener.RabbitListenerEndpointRegistry;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
class MessagingListenerActivator {
    private final RabbitListenerEndpointRegistry rabbitListenerEndpointRegistry;

    void activateListener(String listenerId) {
        rabbitListenerEndpointRegistry.getListenerContainer(listenerId).start();
    }
}
