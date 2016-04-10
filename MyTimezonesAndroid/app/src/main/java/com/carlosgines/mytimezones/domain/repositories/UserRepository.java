package com.carlosgines.mytimezones.domain.repositories;

import com.carlosgines.mytimezones.domain.models.User;

/**
 * Interface that represents a Repository to manage User related data.
 */
public interface UserRepository {

    /**
     * Register a new user and get the auth user.
     *
     * @param userName The user name.
     * @param password The user password.
     * @return The auth user or empty string user if user name already exists.
     */
    User register(final String userName, final String password);

    /**
     * Get an authenticated user in case valid credentials are provided.
     *
     * @param userName The user name.
     * @param password The user password.
     * @return The auth user or empty user if auth failed.
     */
    User signin(final String userName, final String password);

    /**
     * Get the authenticated user if available.
     *
     * @return The authenticated user or null.
     */
    User getAuthUser();

    /**
     * Set the authenticated user.
     * @param user Authenticated user to be registered.
     */
    void setAuthUser(User user);

    /**
     * Unregister user auth token.
     */
    void signout();

    /**
     * Get the user auth token if available.
     * @return the user auth token or empty String if none.
     */
    String getToken();
}
