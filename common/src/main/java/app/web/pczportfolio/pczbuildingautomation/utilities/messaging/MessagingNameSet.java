package app.web.pczportfolio.pczbuildingautomation.utilities.messaging;

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


    public MessagingNameSet(DeviceChannel deviceChannel) {
        this.exchangeName = createExchangeName(deviceChannel);
        this.exchangeDlxName = createExchangeDlxName(deviceChannel);
        this.queueName = createQueueName(deviceChannel);
        this.queueDlxName = createQueueDlxName(deviceChannel);
        this.routingKeyName = createRoutingKey(deviceChannel);
    }

    private String createExchangeName(DeviceChannel deviceChannel) {
        return EXCHANGE_PREFIX + deviceChannel.getChannelRootName();
    }

    private String createExchangeDlxName(DeviceChannel deviceChannel) {
        return EXCHANGE_PREFIX + deviceChannel.getChannelRootName() + DLX_SUFFIX;
    }


    private String createQueueName(DeviceChannel deviceChannel) {
        return QUEUE_PREFIX +
                deviceChannel.getChannelRootName() +
                "-" +
                deviceChannel.getOwner() +
                "-" +
                deviceChannel.getId();
    }

    private String createQueueDlxName(DeviceChannel deviceChannel) {
        return QUEUE_PREFIX +
                deviceChannel.getChannelRootName() +
                DLX_SUFFIX;

    }

    private String createRoutingKey(DeviceChannel deviceChannel) {

        return ROUTING_KEY_PREFIX +
                deviceChannel.getChannelRootName() +
                "-" +
                deviceChannel.getId();
    }


}
