package com.carlosgines.mytimezones.data.repositories;

import com.carlosgines.mytimezones.domain.repositories.UserRepository;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;
import rx.functions.Func1;

/**
 * A UserRepository for managing user data.
 */
public class UserDataRepository implements UserRepository {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    @Inject
    public UserDataRepository() {}

    @Override
    public Observable signin(final String email, final String password) {
        return Observable.create(new Observable.OnSubscribe<Object>() {
            @Override
            public void call(Subscriber<? super Object> subscriber) {
                try {
                    // Simulate network access.
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    subscriber.onError(e);
                }
                for (String credential : DUMMY_CREDENTIALS) {
                    String[] pieces = credential.split(":");
                    if (pieces[0].equals(email) && pieces[1].equals(password)) {
                        // Account exists and password matches, return credentials.
                        subscriber.onNext(new Object());
                        subscriber.onCompleted();
                        return;
                    }
                }
                subscriber.onError(new Exception("Account and password do not match"));
            }
        });
    }
}
