package com.carlosgines.mytimezones.domain.usecases;

import com.carlosgines.mytimezones.domain.models.Timezone;
import com.carlosgines.mytimezones.domain.repositories.TzRepository;
import com.carlosgines.mytimezones.domain.usecases.rx.PostExecutionThread;
import com.carlosgines.mytimezones.domain.usecases.rx.ThreadExecutor;

import javax.inject.Inject;

import rx.Observable;
import rx.Subscriber;

/**
 * Implementation of an UseCase that edits a timezone record.
 */
public class EditTzUseCase extends UseCase {

    // ========================================================================
    // Member variables
    // ========================================================================

    private Timezone mTz;
    private final TzRepository mTzRepository;

    // ========================================================================
    // Constructor
    // ========================================================================

    @Inject
    public EditTzUseCase(TzRepository tzRepository,
                         ThreadExecutor threadExecutor,
                         PostExecutionThread postExecutionThread) {
        super(threadExecutor, postExecutionThread);
        mTzRepository = tzRepository;
    }

    // ========================================================================
    // Public methods
    // ========================================================================

    public void execute(Timezone tz, Subscriber subscriber) {
        mTz = tz;
        super.execute(subscriber);
    }

    // ========================================================================
    // UseCase methods
    // ========================================================================

    @Override
    protected Observable buildUseCaseObservable() {
        return Observable.create(new Observable.OnSubscribe<Timezone>() {
            @Override
            public void call(Subscriber<? super Timezone> subscriber) {
                subscriber.onNext(mTzRepository.edit(mTz));
                subscriber.onCompleted();
            }
        });
    }
}