package com.carlosgines.mytimezones.domain.models;

import java.io.Serializable;

/**
 * An user.
 */
public class User implements Serializable {

    private String _id;
    private String username;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
