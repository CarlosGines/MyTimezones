package com.carlosgines.mytimezones.domain.usecases;

import com.carlosgines.mytimezones.domain.repositories.UserRepository;
import com.carlosgines.mytimezones.domain.usecases.rx.PostExecutionThread;
import com.carlosgines.mytimezones.domain.usecases.rx.ThreadExecutor;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * This is an implementation UseCase that represents an attempt to sign in.
 */
public class SigninUseCase extends UseCase {

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
    public SigninUseCase(UserRepository userRepository,
                         ThreadExecutor threadExecutor,
                         PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserRepository = userRepository;
    }

    // ========================================================================
    // Public methods
    // ========================================================================

    public void execute(final String userName, final String password,
                        Subscriber subscriber) {
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
                final String token = mUserRepository.signin(
                        mUserName,
                        mPassword
                );
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