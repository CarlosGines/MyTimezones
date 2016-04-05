package com.carlosgines.mytimezones.domain.repositories;

import rx.Observable;

/**
 * Interface that represents a Repository to manage User related data.
 */
public interface UserRepository {

    /**
     * Get an Observable which will emit an auth token of an user in case valid
     * credentials are provided.
     *
     * @param userName The user name
     * @param password The user password
     */
    Observable<String> signin(final String userName, final String password);

    /**
     * Get an Observable which register a new user and emit an auth token.
     *
     * @param userName The user name
     * @param password The user password
     */
    Observable<String> register(final String userName, final String password);
}
