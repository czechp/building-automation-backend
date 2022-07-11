# BUILDING AUTOMATION BY *PCzech*

The project provides the possibility to control home automation.

Modern home installations allow to control different devices by using WIFI for example:

- remote light switches,
- remote lock on the door,
- remote shutter,
- air conditioner.

Systems accessed on the market supply paid solutions and require for each device dedicated control system.
In order to avoid unneeded extending infrastructure I am going to supply system which can be integrated with cheap
devices such e.g. arduino or raspberry pi. Basically each system which can make http request and support AMQP protocol
can cooperate with that system.

## TODO:

### Switch device:

- [x] global interface to bound all devices,
- [x] receive feedback from switch device,
- [x] check time since set a new state and set error if is longer that specific value,
- [x] docker container with RabbitMq,
- [x] maven dependency to rabbit mq,
- [x] service to making queue during switch device creation,
- [x] delete queue when device is removing (delete switch device by event),
- [x] trim all whitespaces from username in account,
- [x] service to posting message in queue when switch device state was changed,
- [x] set state of switch device,

### Events 
- [x] rename MessagingChannel to DeviceChannel,
- [x] aop dependency,
- [x] additional method in DeviceChannel ( getName(), getState() ),
- [x] AOP annotations,
- [x] event package,
- [x] event entity,
- [x] event creators,
- [x] warmup,
- [x] create case,
- [x] delete case,
- [x] set new state case,
- [ ] get feedback case
- [x] failed events,
- [ ] query (findAll, findByAccount),
- [ ] remove event (only by admin),
- [ ] switch device dlx queue,
- [ ] mark @Transactional above use cases