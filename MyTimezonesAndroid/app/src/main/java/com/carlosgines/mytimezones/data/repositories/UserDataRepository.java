package com.carlosgines.mytimezones.data.repositories;

import com.carlosgines.mytimezones.data.datastores.SharedPreferencesDataStore;
import com.carlosgines.mytimezones.data.datastores.UserRestDataStore;
import com.carlosgines.mytimezones.domain.models.User;
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
    public User register(final String userName, final String password) {
        return mUserRestDataStore.register(userName, password);
    }

    @Override
    public User signin(final String userName, final String password) {
        return mUserRestDataStore.signin(userName, password);
    }

    @Override
    public User getAuthUser() {
        return mSpDataStore.getAuthUser();
    }

    @Override
    public void setAuthUser(final User user) {
        mSpDataStore.setAuthUser(user);
    }

    @Override
    public void signout() {
        mSpDataStore.deleteAuthUser();
    }

    @Override
    public String getToken() {
        return mSpDataStore.getToken();
    }
}
