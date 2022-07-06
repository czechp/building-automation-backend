package app.web.pczportfolio.pczbuildingautomation.device;

public interface Device <S, F> {
    void setState(S setStateDto);
    void receiveFeedback(F feedbackDto);
    void checkDeviceError();

}
