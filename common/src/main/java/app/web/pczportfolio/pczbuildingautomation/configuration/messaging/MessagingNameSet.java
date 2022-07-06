package app.web.pczportfolio.pczbuildingautomation.configuration.messaging;

import lombok.Getter;

@Getter
class MessagingNameSet {
    private static final String EXCHANGE_PREFIX = "x.";
    private static final String QUEUE_PREFIX = "q.";
    private static final String ROUTING_KEY_PREFIX = "r.";
    private static final String DLX_SUFFIX = ".dlx";

    private String exchangeName;
    private String exchangeDlxName;

    private String queueName;
    private String queueDlxName;
    private String routingKeyName;


    public MessagingNameSet(MessagingChannel messagingChannel) {
        this.exchangeName = createExchangeName(messagingChannel);
        this.exchangeDlxName = createExchangeDlxName(messagingChannel);
        this.queueName = createQueueName(messagingChannel);
        this.queueDlxName = createQueueDlxName(messagingChannel);
        this.routingKeyName = createRoutingKey(messagingChannel);
    }
    
    private String createExchangeName(MessagingChannel messagingChannel) {
        return EXCHANGE_PREFIX + messagingChannel.getChannelRootName();
    }

    private String createExchangeDlxName(MessagingChannel messagingChannel) {
        return EXCHANGE_PREFIX + messagingChannel.getChannelRootName() + DLX_SUFFIX;
    }



    private String createQueueName(MessagingChannel messagingChannel) {
        return QUEUE_PREFIX +
                messagingChannel.getChannelRootName() +
                "-" +
                messagingChannel.getOwner() +
                "-" +
                messagingChannel.getId();
    }

    private String createQueueDlxName(MessagingChannel messagingChannel) {
        return QUEUE_PREFIX +
                messagingChannel.getChannelRootName() +
                DLX_SUFFIX;

    }
    private String createRoutingKey(MessagingChannel messagingChannel) {

        return ROUTING_KEY_PREFIX +
                messagingChannel.getChannelRootName() +
                "-" +
                messagingChannel.getId();
    }


}
