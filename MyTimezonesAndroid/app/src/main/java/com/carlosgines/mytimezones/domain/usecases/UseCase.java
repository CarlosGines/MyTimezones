package com.carlosgines.mytimezones.domain.usecases;

import com.carlosgines.mytimezones.domain.usecases.rx.PostExecutionThread;
import com.carlosgines.mytimezones.domain.usecases.rx.ThreadExecutor;

import rx.Observable;
import rx.Subscriber;
import rx.Subscription;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * <p>Abstract class for a Use Case (Interactor in terms of Clean Architecture).
 * This interface represents a execution unit for different use cases (this means any use case
 * in the application should implement this contract).</p>
 *
 * <p>By convention each UseCase implementation will return the result using a {@link rx.Subscriber}
 * that will execute its job in a background thread and will post the result in the UI thread.</p>
 */
public abstract class UseCase {

    // ==========================================================================
    // Member variables
    // ==========================================================================

    private final ThreadExecutor mThreadExecutor;
    private final PostExecutionThread mPostExecutionThread;

    private Subscription mSubscription = Subscriptions.empty();

    // ==========================================================================
    // Constructor
    // ==========================================================================

    protected UseCase(final ThreadExecutor threadExecutor,
                      final PostExecutionThread postExecutionThread) {
        mThreadExecutor = threadExecutor;
        mPostExecutionThread = postExecutionThread;
    }

    // ==========================================================================
    // Abstract methods
    // ==========================================================================

    /**
     * Builds an Observable which will be used when executing the current UseCase.
     */
    protected abstract Observable buildUseCaseObservable();

    // ==========================================================================
    // Public methods
    // ==========================================================================

    /**
     * Executes the current use case.
     * @param useCaseSubscriber The guy who will be listen to the observable built with
     *                          #buildUseCaseObservable().
     */
    @SuppressWarnings("unchecked")
    protected void execute(final Subscriber useCaseSubscriber) {
        mSubscription = this.buildUseCaseObservable()
                .subscribeOn(Schedulers.from(mThreadExecutor))
                .observeOn(mPostExecutionThread.getScheduler())
                .subscribe(useCaseSubscriber);
    }

    /**
     * Unsubscribes from current {@link rx.Subscription}.
     */
    public void unsubscribe() {
        if (!mSubscription.isUnsubscribed()) {
            mSubscription.unsubscribe();
        }
    }
}
