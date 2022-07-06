# BUILDING AUTOMATION BY *PCzech*

The project provides the possibility to control home automation.

Modern home installations allow to control different devices by using WIFI for example:
 - remote light switches,
 - remote lock on the door,
 - remote shutter,
 - air conditioner.

Systems accessed on the market supply paid solutions and require for each device dedicated control system.
In order to avoid unneeded extending infrastructure I am going to supply system which can be integrated with cheap 
devices such e.g. arduino or raspberry pi. Basically  each system which can make http request and support AMQP protocol
can cooperate with that system.


## TODO:

### Switch device:
- [x] global interface to bound all devices,
- [x] receive feedback from switch device,
- [x] check time since set a new state and set error if is longer that specific value,
- [x] docker container with RabbitMq,
- [x] maven dependency to rabbit mq,
- [ ] service to making queue during switch device creation,
- [ ] delete queue when device is removing,
- [ ] trim all whitespaces from username in account,
- [ ] service to posting message in queue when switch device state was changed,
- [ ] set state of switch device,