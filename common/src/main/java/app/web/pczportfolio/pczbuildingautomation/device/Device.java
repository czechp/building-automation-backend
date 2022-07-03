package app.web.pczportfolio.pczbuildingautomation.device;

public interface Device <T, S, F> {
    T setState(S setStateDto);
    T receiveFeedback(F feedbackDto);
    boolean checkDeviceError();

}
