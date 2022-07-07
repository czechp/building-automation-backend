package app.web.pczportfolio.pczbuildingautomation.utilities.messaging;

import com.rabbitmq.client.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class MessagingConfiguration {
    @Bean
    public RabbitAdmin getRabbitAdmin(@Autowired RabbitTemplate rabbitTemplate){
        return new RabbitAdmin(rabbitTemplate);
    }
}
