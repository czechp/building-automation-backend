package app.web.pczportfolio.pczbuildingautomation.utilities.messaging;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class MessagingNameSetTest {
    @Test
    void correctOfNameTest() {
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
        final var setOfNames = new MessagingNameSet(messagingChannel);
        //then
        assertEquals("x.someRoot", setOfNames.getExchangeName());
        assertEquals("x.someRoot.dlx", setOfNames.getExchangeDlxName());
        assertEquals("q.someRoot.dlx", setOfNames.getQueueDlxName());
        assertEquals("q.someRoot-someOwner-1", setOfNames.getQueueName());
        assertEquals("r.someRoot-1", setOfNames.getRoutingKeyName());
    }
}