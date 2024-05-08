package de.gothaer.gui.presenter;

import de.gothaer.gui.Euro2DollarRechnerView;
import de.gothaer.model.Euro2DollarRechner;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InOrder;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.mockito.verification.VerificationMode;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class Euro2DollarPresenterImplTest {

    @InjectMocks
    private Euro2DollarPresenterImpl objectUnderTest;

    @Mock
    private Euro2DollarRechnerView viewMock;

    @Mock
    private Euro2DollarRechner modelMock;

    private static final String VALID_EURO_VALUE = "10.0";
    @Test
    void onBeenden_happyDay_maskShouldBeClosed() {
        assertDoesNotThrow(()->objectUnderTest.onBeenden());
        verify(viewMock, times(1)).close();
    }
    @Test
    void onPopulateItems_happyDay_maskShouldBeInitialized() {
        assertDoesNotThrow(()->objectUnderTest.onPopulateItems());
        verify(viewMock, times(1)).setDollar("0");
        verify(viewMock, times(1)).setEuro("0");
        verify(viewMock, times(1)).setRechnenEnabled(true);
    }
    @Nested
    class onRechnen{
        @ParameterizedTest
        @CsvSource({",Kein Wert gefunden","NAN,Keine Zahl"})
        void onRechnen_invalidValueInEuroField_errorMessageInDollarField(String fieldValue, String expectedMessage){
            when(viewMock.getEuro()).thenReturn(fieldValue);
            objectUnderTest.onRechnen();
            verify(viewMock).setDollar(expectedMessage);
        }
        @Test
        void onRechnen_unexpectedRuntimeExceptionInUnderlyingService_errorMessageInDollarField(){

            when(viewMock.getEuro()).thenReturn(VALID_EURO_VALUE);
            when(modelMock.calculateEuro2Dollar(anyDouble())).thenThrow(new ArithmeticException("Upps"));
            objectUnderTest.onRechnen();
            verify(viewMock).setDollar("Fehler im Service");
        }


        @Test
        void onRechnen_happyDay_resultInDollarField(){

            when(viewMock.getEuro()).thenReturn(VALID_EURO_VALUE);
            when(modelMock.calculateEuro2Dollar(anyDouble())).thenReturn(4711.1);
            objectUnderTest.onRechnen();
            verify(viewMock).setDollar("4711,10");
            verify(modelMock).calculateEuro2Dollar(10.0);
        }
    }

    @Nested
    class updateRechnenActionState {
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"  ", "Herbert", "\n"})
        void updateRechnenActionState_invalidValueInEuroField_actionDisabled(String invalid) {
            when(viewMock.getEuro()).thenReturn(invalid);
            objectUnderTest.updateRechnenActionState();
            verify(viewMock).setRechnenEnabled(false);
        }

        @Test
        void updateRechnenActionState_validValueInEuroField_actionDisabled() {
            when(viewMock.getEuro()).thenReturn(VALID_EURO_VALUE);
            objectUnderTest.updateRechnenActionState();
            verify(viewMock).setRechnenEnabled(true);
        }
    }

}