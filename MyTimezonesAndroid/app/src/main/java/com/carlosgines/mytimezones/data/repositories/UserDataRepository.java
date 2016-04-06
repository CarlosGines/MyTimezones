package com.carlosgines.mytimezones.data.repositories;

import com.carlosgines.mytimezones.data.datastores.SharedPreferencesDataStore;
import com.carlosgines.mytimezones.data.datastores.UserRestDataStore;
import com.carlosgines.mytimezones.domain.repositories.UserRepository;

import javax.inject.Inject;

/**
 * A UserRepository for managing user data.
 */
public class UserDataRepository implements UserRepository {

    private final UserRestDataStore mUserRestDataStore;
    private final SharedPreferencesDataStore mSpDataStore;

    @Inject
    public UserDataRepository(SharedPreferencesDataStore spDataStore,
                              UserRestDataStore userDataRepository) {
        mUserRestDataStore = userDataRepository;
        mSpDataStore = spDataStore;
    }

    @Override
    public String register(final String userName, final String password) {
        return mUserRestDataStore.register(userName, password);
    }

    @Override
    public String signin(final String userName, final String password) {
        return mUserRestDataStore.signin(userName, password);
    }

    @Override
    public void registerToken(final String token) {
        mSpDataStore.registerToken(token);
    }

    @Override
    public String getToken() {
        return mSpDataStore.getToken();
    }

    @Override
    public void signout() {
        mSpDataStore.deleteToken();
    }
}
