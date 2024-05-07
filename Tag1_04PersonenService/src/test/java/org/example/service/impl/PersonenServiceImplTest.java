package org.example.service.impl;

import org.example.persistence.Person;
import org.example.persistence.PersonenRepository;
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

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class PersonenServiceImplTest {

    @InjectMocks
    private PersonenServiceImpl objectUnderTest;
    @Mock
    private PersonenRepository personenRepositoryMock;

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



}