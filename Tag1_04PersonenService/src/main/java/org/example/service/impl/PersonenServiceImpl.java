package org.example.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.persistence.Person;
import org.example.persistence.PersonenRepository;
import org.example.service.PersonenService;
import org.example.service.PersonenServiceException;

import java.util.UUID;


@RequiredArgsConstructor
public class PersonenServiceImpl implements PersonenService {

    //Dependency-Injection oder Inversion of Control
    private final PersonenRepository repo;
    

    /*
        1.) wenn person = null => PSE
        2.) wenn vorname null oder weniger als 2 Zeichen => PSE
        3.) wenn nachname null oder weniger als 2 Zeichen => PSE
        4.) wenn Vorname = Attila => PSE
        5.) wenn Laufzeitfehler => PSE

        Happy day => person via repo speichern
     */
    @Override
    public void speichern(Person person) throws PersonenServiceException {
        if(person == null) {
            throw new PersonenServiceException("Person darf nicht null sein.");
        }
        if(person.getVorname() == null || person.getVorname().length() < 2) {
            throw new PersonenServiceException("Vorname muss min. 2 Zeichen enthalten.");
        }

    }

  

   
}
