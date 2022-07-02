package app.web.pczportfolio.pczbuildingautomation.device;

public interface Device <T, C, S, F> {
    T createDevice(C createDto);
    T setState(S setStateDto);
    T receiveFeedback(F feedbackDto);
    boolean checkDeviceError();

}
