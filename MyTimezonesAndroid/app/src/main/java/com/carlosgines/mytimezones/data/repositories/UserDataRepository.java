package com.carlosgines.mytimezones.data.repositories;

import com.carlosgines.mytimezones.domain.repositories.UserRepository;

import javax.inject.Inject;

import rx.Observable;

/**
 * A UserRepository for managing user data.
 */
public class UserDataRepository implements UserRepository {

    @Inject
    public UserDataRepository() {}

    @Override
    public Observable signin(String email, String password) {
        return null;
    }
}
