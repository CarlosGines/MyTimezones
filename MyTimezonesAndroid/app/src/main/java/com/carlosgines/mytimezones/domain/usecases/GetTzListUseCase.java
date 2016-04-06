package com.carlosgines.mytimezones.domain.usecases;

import com.carlosgines.mytimezones.domain.models.Timezone;
import com.carlosgines.mytimezones.domain.repositories.TzRepository;
import com.carlosgines.mytimezones.domain.usecases.rx.PostExecutionThread;
import com.carlosgines.mytimezones.domain.usecases.rx.ThreadExecutor;

import java.util.List;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Implementation of an UseCase to get the list of timezone records of the
 * current user.
 */
public class GetTzListUseCase extends UseCase {

    // ========================================================================
    // Member variables
    // ========================================================================

    private final TzRepository mTzRepository;

    // ========================================================================
    // Constructor
    // ========================================================================

    @Inject
    public GetTzListUseCase(TzRepository tzRepository,
                            ThreadExecutor threadExecutor,
                            PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mTzRepository = tzRepository;
    }

    // ========================================================================
    // Public methods
    // ========================================================================

    public void execute(Timezone tz, Subscriber subscriber) {
        super.execute(subscriber);
    }

    // ========================================================================
    // UseCase methods
    // ========================================================================

    @Override
    protected Observable buildUseCaseObservable() {
        return Observable.create(new Observable.OnSubscribe<List<Timezone>>() {
            @Override
            public void call(Subscriber<? super List<Timezone>> subscriber) {
                subscriber.onNext(mTzRepository.get());
                subscriber.onCompleted();
            }
        });
    }
}