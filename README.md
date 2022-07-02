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
- [ ] receive feedback from switch device,
- [ ] set state of switch device, 
- [ ] check time since set a new state and set error if is longer that predicted,