package com.carlosgines.mytimezones.presentation.presenters;

public interface BaseView {

    /**
     * Show the progress screen.
     */
    void showProgress(final boolean show);

    /**
     * Show or hide the loading screen.
     */
    void showNoConnection(final boolean show);

    /**
     * Show a message.
     */
    void showMessage(final String message);

    /**
     * Show the default error message.
     */
    void showErrorMessage(final Throwable e);
}