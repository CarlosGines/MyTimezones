package com.carlosgines.mytimezones.domain.repositories;

/**
 * Interface that represents a Repository to manage User related data.
 */
public interface UserRepository {

    /**
     * Register a new user and get an auth token.
     *
     * @param userName The user name
     * @param password The user password
     * @return The auth token
     */
    String register(final String userName, final String password);

    /**
     * Get an auth token for the user in case valid credentials are provided.
     *
     * @param userName The user name
     * @param password The user password
     * @return The auth token
     */
    String signin(final String userName, final String password);

    /**
     * Register a new auth token necessary for Rest API calls.
     * @param token Auth token to be registered
     */
    void registerToken(String token);
}
