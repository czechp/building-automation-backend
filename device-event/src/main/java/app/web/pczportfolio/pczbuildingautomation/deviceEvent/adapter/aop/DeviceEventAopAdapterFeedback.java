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
class DeviceEventAopAdapterFeedback {
    private final DeviceEventUseCaseCreate deviceEventUseCaseCreate;

    @AfterReturning(
            value = "@annotation(app.web.pczportfolio.pczbuildingautomation.deviceEvent.annotation.DeviceEventFeedback)",
            returning = "deviceChannel")
    public void createFeedbackDeviceEventAdvice(JoinPoint joinPoint, Object deviceChannel) {
        DeviceEventAopDeviceChannelMapper.castToDeviceChannel(deviceChannel)
                .ifPresent(this::createFeedbackDeviceEvent);
    }


    @AfterThrowing(value = "@annotation(app.web.pczportfolio.pczbuildingautomation.deviceEvent.annotation.DeviceEventFeedback)")
    public void createFailedFeedbackDeviceEventAdvice(JoinPoint joinPoint) {
        deviceEventUseCaseCreate.createDeviceEventFailed(DeviceEventType.FEEDBACK_FROM_DEVICE);
    }

    private void createFeedbackDeviceEvent(DeviceChannel deviceChannel) {
        deviceEventUseCaseCreate.createDeviceEvent(deviceChannel, DeviceEventType.FEEDBACK_FROM_DEVICE);
    }
}
