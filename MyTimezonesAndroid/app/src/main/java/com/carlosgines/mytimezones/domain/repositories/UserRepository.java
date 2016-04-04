package com.carlosgines.mytimezones.domain.repositories;

import rx.Observable;

/**
 * Interface that represents a Repository to manage User related data.
 */
public interface UserRepository {

    /**
     * Get an Observable which will emit credentials of an user.
     *
     * @param email The user email
     * @param password The user password
     */
    Observable signin(final String email, final String password);
}
