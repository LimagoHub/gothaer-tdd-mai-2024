package de.gothaer.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.naming.ServiceUnavailableException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
@ExtendWith(MockitoExtension.class)
class BestellServiceTest {

    @InjectMocks
    private BestellService objectUndertest;
    @Mock
    private CreditCardService creditCardServiceMock;
    @Mock
    private EventService eventServiceMock;

   /* @BeforeEach
    void setUp()  {
        creditCardServiceMock = Mockito.mock(CreditCardService.class);
        eventServiceMock = Mockito.mock(EventService.class);
        objectUndertest = new BestellService(creditCardServiceMock, eventServiceMock);
    }

    */

    @Test
    void kundehatgeld() throws Exception{

        // Arrange
        // RecordMode
        when(creditCardServiceMock.check(anyString(), anyDouble())).thenReturn(true);

        // Action
        objectUndertest.bestellen();

        // Assertion
        verify(eventServiceMock).fireEvent("OK");

    }

    @Test
    void kundehatKeingeld() throws Exception{

        // Arrange
        // RecordMode
        Mockito.when(creditCardServiceMock.check(Mockito.anyString(), Mockito.anyDouble())).thenReturn(false);

        // Action
        objectUndertest.bestellen();

        // Assertion
        Mockito.verify(eventServiceMock).fireEvent("Besser Nicht");

    }

    @Test
    void serviceDown() throws Exception{

        // Arrange
        // RecordMode
        Mockito.when(creditCardServiceMock.check(Mockito.anyString(), Mockito.anyDouble())).thenThrow(new ServiceUnavailableException());

        // Action
        objectUndertest.bestellen();

        // Assertion
        Mockito.verify(eventServiceMock).fireEvent("nicht erreichbar");

    }


}