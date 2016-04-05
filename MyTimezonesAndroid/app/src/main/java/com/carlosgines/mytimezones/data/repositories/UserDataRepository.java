package com.carlosgines.mytimezones.data.repositories;

import com.carlosgines.mytimezones.data.datastores.UserRestDataStore;
import com.carlosgines.mytimezones.domain.repositories.UserRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * A UserRepository for managing user data.
 */
public class UserDataRepository implements UserRepository {

    private final UserRestDataStore mUserRestDataStore;

    @Inject
    public UserDataRepository(UserRestDataStore userDataRepository) {
        mUserRestDataStore = userDataRepository;
    }

    @Override
    public Observable<String> signin(final String userName,
                                     final String password) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(
                        mUserRestDataStore.signin(userName, password)
                );
            }
        });
    }

    @Override
    public Observable<String> register(final String userName,
                                       final String password) {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext(
                        mUserRestDataStore.register(userName, password)
                );
            }
        });
    }
}
