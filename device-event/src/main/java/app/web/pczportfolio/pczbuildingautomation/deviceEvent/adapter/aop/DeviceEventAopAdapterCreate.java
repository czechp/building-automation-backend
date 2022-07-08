package app.web.pczportfolio.pczbuildingautomation.deviceEvent.adapter.aop;

import app.web.pczportfolio.pczbuildingautomation.utilities.messaging.DeviceChannel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
class DeviceEventAopAdapterCreate {

    @AfterReturning(
            value = "@annotation(app.web.pczportfolio.pczbuildingautomation.deviceEvent.annotation.CreateDeviceEvent)",
            returning = "deviceChannel"
    )
    public void createDeviceAdvice(JoinPoint joinPoint, Object deviceChannel) {
        System.out.println(deviceChannel);
    }
}
