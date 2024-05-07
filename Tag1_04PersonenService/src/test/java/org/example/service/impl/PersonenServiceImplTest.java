package org.example.service.impl;

import org.example.persistence.Person;
import org.example.persistence.PersonenRepository;
import org.example.service.BlackListService;
import org.example.service.PersonenServiceException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.WARN)
class PersonenServiceImplTest {

    @InjectMocks
    private PersonenServiceImpl objectUnderTest;
    @Mock
    private PersonenRepository personenRepositoryMock;
    @Mock
    private BlackListService blackListServiceMock;

    @Test
    void speichern_personIsNull_throwsPersonenServiceException() throws PersonenServiceException {
        // Arrange
        // Action + Assertion
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(null));
        assertEquals("Person darf nicht null sein.", ex.getMessage());

    }

    @Test
    void speichern_vornameIsNull_throwsPersonenServiceException() throws PersonenServiceException {
        // Arrange
        final Person invalidPerson = Person.builder().vorname(null).nachname("Doe").build();
        // Action + Assertion
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(invalidPerson));
        assertEquals("Vorname muss min. 2 Zeichen enthalten.", ex.getMessage());

    }

    @Test
    void speichern_vornameZuKurz_throwsPersonenServiceException() throws PersonenServiceException {
        // Arrange
        final Person invalidPerson = Person.builder().vorname("J").nachname("Doe").build();
        // Action + Assertion
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(invalidPerson));
        assertEquals("Vorname muss min. 2 Zeichen enthalten.", ex.getMessage());

    }
    @Test
    void speichern_NachnameZuKurz_throwsPersonenServiceException() throws PersonenServiceException {
        // Arrange
        final Person invalidPerson = Person.builder().vorname("Jay").nachname("D").build();
        // Action + Assertion
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(invalidPerson));
        assertEquals("Nachname muss min. 2 Zeichen enthalten.", ex.getMessage());

    }
    @Test
    void speichern_PersonIsBlackListed_throwsPersonenServiceException() throws PersonenServiceException {
        // Arrange
        final Person blackListedPerson = Person.builder().vorname("John").nachname("Doe").build();
        // Action + Assertion
        when(blackListServiceMock.isBlacklisted(any())).thenReturn(true);
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(blackListedPerson));
        assertEquals("Unerwünschte Person", ex.getMessage());
        verify(blackListServiceMock).isBlacklisted(blackListedPerson);
    }
    @Test
    void speichern_UnexpectedRuntimeExceptionInUnderlyingService_throwsPersonenServiceException()  throws PersonenServiceException{
        // Arrange
        final Person validPerson= Person.builder().vorname("Alex").nachname("Doe").build();
        doThrow(new ArithmeticException()).when(personenRepositoryMock).save(any(Person.class));
        // Action + Assertion
        when(blackListServiceMock.isBlacklisted(validPerson)).thenReturn(false);
        PersonenServiceException ex = assertThrows(PersonenServiceException.class, () -> objectUnderTest.speichern(validPerson));
        assertEquals("Person konnte nicht gespeichert werden", ex.getMessage());
        assertNotNull(ex.getCause());
        assertEquals(ArithmeticException.class,ex.getCause().getClass());
    }
    @Test
    void speichern_HappyDay_PersonPassedToRepo() throws PersonenServiceException {
        // Arrange
        final Person validPerson= Person.builder().vorname("Alex").nachname("Doe").build();
        // Action
       when(blackListServiceMock.isBlacklisted(validPerson)).thenReturn(false);
        objectUnderTest.speichern(validPerson);
        verify(personenRepositoryMock,times(1)).save(validPerson);
    }

}