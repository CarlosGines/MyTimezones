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

    // ==========================================================================
    // Member variables
    // ==========================================================================

    private String mUserName;
    private String mPassword;
    private final UserRepository mUserRepository;

    // ==========================================================================
    // Constructor
    // ==========================================================================

    @Inject
    public RegisterUseCase(UserRepository userRepository, ThreadExecutor threadExecutor,
                           PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserRepository = userRepository;
    }

    // ==========================================================================
    // Public methods
    // ==========================================================================

    public void execute(final String userName, final String password, Subscriber subscriber) {
        mUserName = userName;
        mPassword = password;
        super.execute(subscriber);
    }
    // ==========================================================================
    // UseCase methods
    // ==========================================================================

    @Override
    protected Observable buildUseCaseObservable() {
        return mUserRepository.register(mUserName, mPassword);
    }
}