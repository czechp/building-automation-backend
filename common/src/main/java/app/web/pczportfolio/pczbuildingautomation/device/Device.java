package app.web.pczportfolio.pczbuildingautomation.device;

public interface Device<S, F> {
    void setNewState(S setStateDto);

    void receiveFeedback(F feedbackDto);

    void checkDeviceError();

}
