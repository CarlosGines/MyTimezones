package com.carlosgines.mytimezones.domain.models;

import java.io.Serializable;
import java.text.DateFormat;
import java.util.Date;
import java.util.SimpleTimeZone;

import lombok.EqualsAndHashCode;

/**
 * A time zone.
 */
@EqualsAndHashCode(of={"_id"})
public class Timezone implements Serializable {

    // ========================================================================
    // Member variables
    // ========================================================================

    private String _id;
    private String name;
    private String city;
    private int timeDiff;
    private User author;
    private Date created;
    private Date updated;

    // ========================================================================
    // Constructors
    // ========================================================================

    public Timezone() {
    }

    public Timezone(final String name, final String city, final int timeDiff) {
        this.name = name;
        this.city = city;
        this.timeDiff = timeDiff;
    }

    // ========================================================================
    // Getters/Setters
    // ========================================================================

    public String get_id() {
        return _id;
    }

    public void set_id(final String _id) {
        this._id = _id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    public int getTimeDiff() {
        return timeDiff;
    }

    public void setTimeDiff(final int timeDiff) {
        this.timeDiff = timeDiff;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(final User author) {
        this.author = author;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(final Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(final Date updated) {
        this.updated = updated;
    }

// ========================================================================
// Public methods
// ========================================================================

    /**
     * @return A properly formatted version of the current time and date
     * according to this time zone.
     */
    public String getFormattedCurrentDateTime() {
        final DateFormat df = DateFormat.getDateTimeInstance();
        df.setTimeZone(new SimpleTimeZone(getTimeDiff() * 3600 * 1000, getName()));
        return df.format(new Date());
    }
}
