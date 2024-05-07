package org.example.service;

import org.example.persistence.Person;

public interface PersonenService {

    void speichern(Person person) throws PersonenServiceException;

}
