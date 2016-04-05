package com.carlosgines.mytimezones.presentation.presenters;

import com.android.volley.NoConnectionError;

import java.util.concurrent.TimeoutException;

/**
 * Default subscriber base class to be used whenever you want default error handling.
 */
public class DefaultSubscriber<T> extends rx.Subscriber<T> {

    // ==========================================================================
    // Member variables
    // ==========================================================================

    private BaseView mBaseView;

    // ==========================================================================
    // Constructor
    // ==========================================================================

    public DefaultSubscriber(BaseView baseView) {
        mBaseView = baseView;
    }

    // ==========================================================================
    // rx.Subscriber<T> methods
    // ==========================================================================

    @Override
    public void onNext(T t) {
        // no-op by default.
    }

    @Override
    public void onCompleted() {
        // no-op by default.
    }

    @Override
    public final void onError(Throwable e) {
        e.printStackTrace();
        Throwable cause = e.getCause();
        if (cause != null) {
            if (cause instanceof TimeoutException
                    || cause instanceof NoConnectionError
                    ) {
                mBaseView.showNoConnection(true);
                return;
            }
        }
        mBaseView.showErrorMessage(e);
        onError();
    }

    // ==========================================================================
    // Public methods
    // ==========================================================================

    /**
     * Executed after {@code DefaultSubscriber.onError(Throwable e)} when generic error processing
     * is done.
     */
    public void onError() {
    }
}
