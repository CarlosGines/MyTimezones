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

    // ==========================================================================
    // Member variables
    // ==========================================================================

    private String mEmail;
    private String mPassword;
    private final UserRepository mUserRepository;

    // ==========================================================================
    // Constructor
    // ==========================================================================

    @Inject
    public SigninUseCase(UserRepository userRepository, ThreadExecutor threadExecutor,
                         PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserRepository = userRepository;
    }

    // ==========================================================================
    // Public methods
    // ==========================================================================

    public void execute(final String email, final String password, Subscriber subscriber) {
        mEmail = email;
        mPassword = password;
        super.execute(subscriber);
    }
    // ==========================================================================
    // UseCase methods
    // ==========================================================================

    @Override
    protected Observable buildUseCaseObservable() {
        return mUserRepository.signin(mEmail, mPassword);
    }
}