package de.gothaer.service;

import javax.naming.ServiceUnavailableException;

public interface CreditCardService {
    boolean check(String number, double saldo) throws ServiceUnavailableException;
}
