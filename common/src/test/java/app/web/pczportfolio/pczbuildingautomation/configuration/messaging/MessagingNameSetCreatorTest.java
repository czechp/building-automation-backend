package app.web.pczportfolio.pczbuildingautomation.configuration.messaging;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.*;

class MessagingNameSetCreatorTest {
    @Test
    void correctOfNameTest(){
        //given
        final var messagingChannel = new MessagingChannel() {
            @Override
            public long getId() {
                return 1L;
            }

            @Override
            public String getOwner() {
                return "someOwner";
            }

            @Override
            public String getChannelRootName() {
                return "someRoot";
            }
        };
        //when
        final var setOfNames = new MessagingNameSetCreator(messagingChannel);
        //then
        assertEquals("x.someRoot", setOfNames.getExchangeName());
        assertEquals("q.someRoot-someOwner-1", setOfNames.getQueueName());
        assertEquals("r.someRoot-1", setOfNames.getRoutingKeyName());
    }
}