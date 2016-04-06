package com.carlosgines.mytimezones.domain.models;

/**
 * A time zone.
 */
public class Timezone {

    // ========================================================================
    // Member variables
    // ========================================================================

    private String name;
    private String city;
    private int timeDif;

    // ========================================================================
    // Constructor
    // ========================================================================

    public Timezone(String name, String city, int timeDif) {
        this.name = name;
        this.city = city;
        this.timeDif = timeDif;
    }

    // ========================================================================
    // Getters/Setters
    // ========================================================================

    public int getTimeDif() {
        return timeDif;
    }

    public void setTimeDif(int timeDif) {
        this.timeDif = timeDif;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
