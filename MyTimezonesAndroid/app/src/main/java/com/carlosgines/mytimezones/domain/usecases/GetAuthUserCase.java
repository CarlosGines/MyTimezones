package com.carlosgines.mytimezones.domain.usecases;

import com.carlosgines.mytimezones.domain.models.User;
import com.carlosgines.mytimezones.domain.repositories.UserRepository;
import com.carlosgines.mytimezones.domain.usecases.rx.PostExecutionThread;
import com.carlosgines.mytimezones.domain.usecases.rx.ThreadExecutor;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * This is an implementation UseCase that checks if there is a known
 * authenticated user locally.
 */
public class GetAuthUserCase extends UseCase {

    // ========================================================================
    // Member variables
    // ========================================================================

    private final UserRepository mUserRepository;

    // ========================================================================
    // Constructor
    // ========================================================================

    @Inject
    public GetAuthUserCase(final UserRepository userRepository,
                           final ThreadExecutor threadExecutor,
                           final PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mUserRepository = userRepository;
    }

    // ========================================================================
    // Public methods
    // ========================================================================

    public void execute(final Subscriber subscriber) {
        super.execute(subscriber);
    }

    // ========================================================================
    // UseCase methods
    // ========================================================================

    @Override
    protected Observable buildUseCaseObservable() {
        return Observable.create(new Observable.OnSubscribe<User>() {
            @Override
            public void call(Subscriber<? super User> subscriber) {
                subscriber.onNext(mUserRepository.getAuthUser());
                subscriber.onCompleted();
            }
        });
    }
}