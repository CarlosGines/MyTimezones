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
    private int timeDiff;

    // ========================================================================
    // Constructor
    // ========================================================================

    public Timezone(String name, String city, int timeDiff) {
        this.name = name;
        this.city = city;
        this.timeDiff = timeDiff;
    }

    // ========================================================================
    // Getters/Setters
    // ========================================================================

    public int getTimeDiff() {
        return timeDiff;
    }

    public void setTimeDiff(int timeDiff) {
        this.timeDiff = timeDiff;
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
