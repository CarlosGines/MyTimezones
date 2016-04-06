package com.carlosgines.mytimezones.domain.repositories;

/**
 * Interface that represents a Repository to manage User related data.
 */
public interface UserRepository {

    /**
     * Register a new user and get an auth token.
     *
     * @param userName The user name.
     * @param password The user password.
     * @return The auth token or empty string if user name already exists.
     */
    String register(final String userName, final String password);

    /**
     * Get an auth token for the user in case valid credentials are provided.
     *
     * @param userName The user name.
     * @param password The user password.
     * @return The auth token or empty string if auth failed.
     */
    String signin(final String userName, final String password);

    /**
     * Register a new auth token.
     * @param token Auth token to be registered.
     */
    void registerToken(String token);

    /**
     * Get the user auth token if available.
     * @return the user auth token or empty String if none.
     */
    String getToken();

    /**
     * Unregister user auth token.
     */
    void signout();
}
