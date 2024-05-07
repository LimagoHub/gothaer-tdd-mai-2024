package org.example.service;

import org.example.persistence.Person;

public interface BlackListService {
    boolean isBlacklisted(Person possibleBlackListedPerson);

}
