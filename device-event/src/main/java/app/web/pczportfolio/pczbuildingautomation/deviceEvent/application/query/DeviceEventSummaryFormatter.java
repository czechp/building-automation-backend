package app.web.pczportfolio.pczbuildingautomation.deviceEvent.application.query;

import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEvent;
import app.web.pczportfolio.pczbuildingautomation.deviceEvent.domain.DeviceEventType;

public class DeviceEventSummaryFormatter {
    public static String createSummary(DeviceEvent deviceEvent) {
        if (deviceEvent.getDeviceEventType() == DeviceEventType.REJECTED_MESSAGE)
            return prepareRejectedMsgEventSummary(deviceEvent);
        else
            return deviceEvent.isFailed() ? prepareFailedSummary(deviceEvent) : prepareNormalSummary(deviceEvent);
    }

    private static String prepareNormalSummary(DeviceEvent deviceEvent) {
        return getContentPrefix(deviceEvent)
                .append("Device type: ")
                .append(deviceEvent.getDeviceEventType())
                .append("\n")
                .append("Device name: ")
                .append(deviceEvent.getDeviceName())
                .append("\n")
                .append("Expected state: ")
                .append(deviceEvent.getExpectedState())
                .append("\n")
                .append("Actual state: ")
                .append(deviceEvent.getState())
                .append("\n")
                .toString();
    }

    private static String prepareFailedSummary(DeviceEvent deviceEvent) {
        return getContentPrefix(deviceEvent).toString();
    }

    private static String prepareRejectedMsgEventSummary(DeviceEvent deviceEvent) {
        return getContentPrefix(deviceEvent)
                .append("Device name: ")
                .append(deviceEvent.getDeviceName())
                .append("\n")
                .append("Expected state: ")
                .append(deviceEvent.getExpectedState())
                .toString();
    }

    private static StringBuilder getContentPrefix(DeviceEvent deviceEvent) {
        return new StringBuilder()
                .append("User: ")
                .append(deviceEvent.getOwner())
                .append("\n")
                .append("Event type: ")
                .append(deviceEvent.getDeviceEventType().getFullName())
                .append("\n")
                .append("Status: ")
                .append(deviceEvent.isFailed() ? "FAILED" : "SUCCESS")
                .append("\n");

    }
}
