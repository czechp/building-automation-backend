package app.web.pczportfolio.pczbuildingautomation.switchDevice.application.service;

import app.web.pczportfolio.pczbuildingautomation.account.dto.AccountFacadeDto;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.application.port.SwitchDevicePortFindCurrentUserAccount;
import app.web.pczportfolio.pczbuildingautomation.switchDevice.domain.SwitchDevice;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SwitchDeviceOwnerValidatorTest {
    @Mock
    SwitchDevicePortFindCurrentUserAccount switchDevicePortFindCurrentUserAccount;

    SwitchDeviceOwnerValidator switchDeviceOwnerValidator;

    @BeforeEach
    void init() {
        this.switchDeviceOwnerValidator = new SwitchDeviceOwnerValidator(switchDevicePortFindCurrentUserAccount);
    }

    @Test
    void currentUserIsOwnerTest() {
        //given
        final var switchDeviceOwner = "Some user";
        final var switchDeviceToValidate = SwitchDevice
                .builder()
                .withOwner(switchDeviceOwner)
                .build();
        final var currentUserAccount = new AccountFacadeDto(1L, switchDeviceOwner, "USER");
        //when
        when(switchDevicePortFindCurrentUserAccount.findCurrentUserAccount()).thenReturn(Optional.of(currentUserAccount));
        boolean resultOfValidation = switchDeviceOwnerValidator.currentUserIsOwner(switchDeviceToValidate);
        //then
        assertTrue(resultOfValidation);
    }

    @Test
    void currentUserIsOwnerIsAdminTest() {
        //given
        final var switchDeviceOwner = "Some user";
        final var switchDeviceToValidate = SwitchDevice
                .builder()
                .withOwner("Different username")
                .build();
        final var currentUserAccount = new AccountFacadeDto(1L, switchDeviceOwner, "ADMIN");
        //when
        when(switchDevicePortFindCurrentUserAccount.findCurrentUserAccount()).thenReturn(Optional.of(currentUserAccount));
        boolean resultOfValidation = switchDeviceOwnerValidator.currentUserIsOwner(switchDeviceToValidate);
        //then
        assertTrue(resultOfValidation);
    }


    @Test
    void currentUserIsNotOwnerTest() {
        //given
        final var switchDeviceOwner = "Some user";
        final var switchDeviceToValidate = SwitchDevice
                .builder()
                .withOwner("Different username")
                .build();
        final var currentUserAccount = new AccountFacadeDto(1L, switchDeviceOwner, "USER");
        //when
        when(switchDevicePortFindCurrentUserAccount.findCurrentUserAccount()).thenReturn(Optional.of(currentUserAccount));
        boolean resultOfValidation = switchDeviceOwnerValidator.currentUserIsOwner(switchDeviceToValidate);
        //then
        assertFalse(resultOfValidation);
    }

}