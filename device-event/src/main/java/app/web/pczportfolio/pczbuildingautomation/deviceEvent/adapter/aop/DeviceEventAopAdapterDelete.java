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

import java.util.List;
import java.util.Optional;

@Aspect
@Component
@AllArgsConstructor
class DeviceEventAopAdapterDelete {
    private final DeviceEventUseCaseCreate deviceEventUseCaseCreate;

    @AfterReturning(
            value = "@annotation(app.web.pczportfolio.pczbuildingautomation.deviceEvent.annotation.DeviceEventDelete)",
            returning = "deviceChannel"
    )
    public void deleteDeviceEventAdvice(JoinPoint joinPoint, Object deviceChannel) {
        if (deviceChannel instanceof DeviceChannel)
            createSingleRemoveEvent(deviceChannel);
        if (deviceChannel instanceof List)
            createManyRemoveEvent(deviceChannel);
    }

    @AfterThrowing("@annotation(app.web.pczportfolio.pczbuildingautomation.deviceEvent.annotation.DeviceEventDelete)")
    public void deleteFailedDeviceEvent(JoinPoint joinPoint) {
        deviceEventUseCaseCreate.createDeviceEventFailed(DeviceEventType.DELETE);
    }

    private void createSingleRemoveEvent(Object deviceChannel) {
        DeviceEventAopDeviceChannelMapper.castToDeviceChannel(deviceChannel)
                .ifPresent(this::createDeleteEvent);
    }

    private void createDeleteEvent(DeviceChannel channel) {
        deviceEventUseCaseCreate.createDeviceEvent(channel, DeviceEventType.DELETE);
    }


    private void createManyRemoveEvent(Object deviceChannel) {
        final var deviceChannelList = (List<DeviceChannel>) deviceChannel;
        deviceChannelList.stream()
                .map(DeviceEventAopDeviceChannelMapper::castToDeviceChannel)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .forEach(this::createDeleteEvent);
    }
}
