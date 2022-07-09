package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.aop;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.useCase.DeviceEventUseCaseCreate;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.EventType;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
@AllArgsConstructor
class DeviceEventAopAdapterCreate {
    private final DeviceEventUseCaseCreate deviceEventUseCaseCreate;
    private final EventType createEventType = EventType.CREATE;

    @AfterReturning(
            value = "@annotation(app.web.pczportfolio.pczbuildingautomation.deviceEvent.annotation.CreateDeviceEvent)",
            returning = "deviceChannel"
    )
    public void createDeviceEventAdvice(JoinPoint joinPoint, Object deviceChannel) {
        DeviceEventAopDeviceChannelMapper.castToDeviceChannel(deviceChannel)
                .ifPresent(channel -> deviceEventUseCaseCreate.createDeviceEvent(channel, createEventType));
    }

    @AfterThrowing(value = "@annotation(app.web.pczportfolio.pczbuildingautomation.deviceEvent.annotation.CreateDeviceEvent)")
    public void createFailedDeviceEventAdvice() {
        deviceEventUseCaseCreate.createDeviceEventFailed(createEventType);
    }
}
