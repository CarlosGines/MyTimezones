package com.carlosgines.mytimezones.domain.models;

import java.io.Serializable;

/**
 * An user.
 */
public class User implements Serializable {

    private String _id;
    private String username;
    private boolean admin;
    private String token;

    public String get_id() {
        return _id;
    }

    public void set_id(final String _id) {
        this._id = _id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(final boolean admin) {
        this.admin = admin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(final String token) {
        this.token = token;
    }
}
