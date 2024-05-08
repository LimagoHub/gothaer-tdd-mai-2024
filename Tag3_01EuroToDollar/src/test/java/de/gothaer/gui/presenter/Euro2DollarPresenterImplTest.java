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


@MockitoSettings(strictness = Strictness.STRICT_STUBS)
@ExtendWith(MockitoExtension.class)
class Euro2DollarPresenterImplTest {

    private final String VALID_EURO_VALUE ="10.0";

    @InjectMocks
    private Euro2DollarPresenterImpl objectUnderTest;

    @Mock
    private Euro2DollarRechnerView viewMock;

    @Mock
    private Euro2DollarRechner modelMock;


    @Test
    void onBeenden_happyDay_maskDisposed() {
        objectUnderTest.onBeenden();
        verify(viewMock).close();
    }

    @Test
    void onPopulateItems_(){
        objectUnderTest.onPopulateItems();
        verify(viewMock).setEuro("0");
        verify(viewMock).setDollar("0");
        verify(viewMock).setRechnenEnabled(true);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings={"Not a number"})
    void onRechnen_NullValueInEuroField_errorMessageInDollarField(String euroFieldValue) {
        InOrder inOrder = inOrder(viewMock);
        //Arrange
        when(viewMock.getEuro()).thenReturn(euroFieldValue);
        objectUnderTest.onRechnen();
        inOrder.verify(viewMock).getEuro();
        inOrder.verify(viewMock).setDollar("Keine Zahl");
    }

    @Test
    void onRechnen_unexcpectedExceptionInUnderlyingServce_errorMessageInDollarField() {
        //  Arrange
        InOrder inOrder = inOrder(viewMock, modelMock);
        when(viewMock.getEuro()).thenReturn(VALID_EURO_VALUE);
        when(modelMock.calculateEuro2Dollar(anyDouble())).thenThrow(ArrayIndexOutOfBoundsException.class);

        // Action
        objectUnderTest.onRechnen();

        // Assertion
        inOrder.verify(viewMock).getEuro();
        inOrder.verify(modelMock).calculateEuro2Dollar(anyDouble());
        inOrder.verify(viewMock).setDollar("Fehler im Service");
    }

//    @Test
//    void onRechnen_valiEuroValueInEuroField_valuePassedToService() {
//        //  Arrange
//        InOrder inOrder = inOrder(viewMock, modelMock);
//        when(viewMock.getEuro()).thenReturn(VALID_EURO_VALUE);
//
//        // Action
//        objectUnderTest.onRechnen();
//
//        // Assertion
//        inOrder.verify(viewMock).getEuro();
//        inOrder.verify(modelMock).calculateEuro2Dollar(10.0);
//
//    }

    @Test
    void onRechnen_HappyDay_dollarValueInDollarField() {
        //  Arrange
        //InOrder inOrder = inOrder(viewMock, modelMock);
        when(viewMock.getEuro()).thenReturn(VALID_EURO_VALUE);
        when(modelMock.calculateEuro2Dollar(10.0)).thenReturn(4711.5);

        // Action
        objectUnderTest.onRechnen();

        // Assertion
        verify(viewMock).getEuro();
        verify(modelMock).calculateEuro2Dollar(10.0);
        verify(viewMock).setDollar("4711,50");
    }


}