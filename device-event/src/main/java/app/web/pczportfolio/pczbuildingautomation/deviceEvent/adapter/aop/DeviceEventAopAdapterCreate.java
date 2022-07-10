package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.aop;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.useCase.DeviceEventUseCaseCreate;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEventType;
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
    private final DeviceEventType createDeviceEventType = DeviceEventType.CREATE;

    @AfterReturning(
            value = "@annotation(app.web.pczportfolio.pczbuildingautomation.deviceEvent.annotation.DeviceEventCreate)",
            returning = "deviceChannel"
    )
    public void createDeviceEventAdvice(JoinPoint joinPoint, Object deviceChannel) {
        DeviceEventAopDeviceChannelMapper.castToDeviceChannel(deviceChannel)
                .ifPresent(channel -> deviceEventUseCaseCreate.createDeviceEvent(channel, createDeviceEventType));
    }

    @AfterThrowing(value = "@annotation(app.web.pczportfolio.pczbuildingautomation.deviceEvent.annotation.DeviceEventCreate)")
    public void createFailedDeviceEventAdvice() {
        deviceEventUseCaseCreate.createDeviceEventFailed(createDeviceEventType);
    }
}
