package de.gothaer.tiere;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;

@Data
@NoArgsConstructor

public class Schwein {

    private String name="Nobody";
    @Setter(AccessLevel.PRIVATE)
    private int gewicht=10;

    public Schwein(String name) {
        setName(name);

    }

    public final void setName(String name) {
        if(name == null || "Elsa".equalsIgnoreCase(name)) throw new IllegalArgumentException("Unerlaubter Name");
        this.name = name;
    }


    public void fuettern() {
        setGewicht(getGewicht() + 1);
    }


}
