package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.aop;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.useCase.DeviceEventUseCaseCreate;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEventType;
import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannel;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Component
@Aspect
@AllArgsConstructor
class DeviceEventAopAdapterSetNewStateRequest {
    private final DeviceEventUseCaseCreate deviceEventUseCaseCreate;

    @AfterReturning(value = "@annotation(app.web.pczportfolio.pczbuildingautomation.deviceEvent.annotation.DeviceEventSetNewStateRequest)", returning = "deviceChannel")
    public void setNewStateRequestEventAdvice(JoinPoint joinPoint, Object deviceChannel) {
        DeviceEventAopDeviceChannelMapper.castToDeviceChannel(deviceChannel)
                .ifPresent(this::createSetNewStateRequestEvent);
    }

    @AfterThrowing(value = "@annotation(app.web.pczportfolio.pczbuildingautomation.deviceEvent.annotation.DeviceEventSetNewStateRequest)")
    public void setNewStateRequestFailedEventAdvice(JoinPoint joinPoint) {
        createSetNewStateRequestFailedEvent();
    }

    private void createSetNewStateRequestEvent(DeviceChannel deviceChannel) {
        deviceEventUseCaseCreate.createDeviceEvent(deviceChannel, DeviceEventType.NEW_STATE_REQUEST);
    }


    private void createSetNewStateRequestFailedEvent() {
        deviceEventUseCaseCreate.createDeviceEventFailed(DeviceEventType.NEW_STATE_REQUEST);
    }
}
