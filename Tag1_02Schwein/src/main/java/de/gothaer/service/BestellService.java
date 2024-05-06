package de.gothaer.service;

import lombok.RequiredArgsConstructor;

import javax.naming.ServiceUnavailableException;

@RequiredArgsConstructor
public class BestellService {

    private final CreditCardService creditCardService;
    private final EventService eventService;


    public void bestellen() {

        try {
            if(creditCardService.check("abc", 10)) {
                eventService.fireEvent("OK");
            } else {
                eventService.fireEvent("Besser Nicht");
            }
        } catch (ServiceUnavailableException e) {
            eventService.fireEvent("nicht erreichbar");
        }
    }
}
