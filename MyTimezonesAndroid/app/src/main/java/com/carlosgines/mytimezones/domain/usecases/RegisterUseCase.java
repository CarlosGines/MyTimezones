package com.carlosgines.mytimezones.domain.usecases;

import com.carlosgines.mytimezones.domain.repositories.UserRepository;
import com.carlosgines.mytimezones.domain.usecases.rx.PostExecutionThread;
import com.carlosgines.mytimezones.domain.usecases.rx.ThreadExecutor;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * This is an implementation UseCase that represents an attempt to register.
 */
public class RegisterUseCase extends UseCase {

    // ========================================================================
    // Member variables
    // ========================================================================

    private String mUserName;
    private String mPassword;
    private final UserRepository mUserRepository;

    // ========================================================================
    // Constructor
    // ========================================================================

    @Inject
    public RegisterUseCase(final UserRepository userRepository,
                           final ThreadExecutor threadExecutor,
                           final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserRepository = userRepository;
    }

    // ========================================================================
    // Public methods
    // ========================================================================

    public void execute(final String userName, final String password,
                        final Subscriber subscriber) {
        mUserName = userName;
        mPassword = password;
        super.execute(subscriber);
    }
    // ========================================================================
    // UseCase methods
    // ========================================================================

    @Override
    protected Observable buildUseCaseObservable() {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                final String token = mUserRepository.register(mUserName, mPassword);
                if (token.isEmpty()) {
                    subscriber.onNext(false);
                    subscriber.onCompleted();
                } else {
                    mUserRepository.registerToken(token);
                    subscriber.onNext(true);
                    subscriber.onCompleted();
                }
            }
        });
    }
}