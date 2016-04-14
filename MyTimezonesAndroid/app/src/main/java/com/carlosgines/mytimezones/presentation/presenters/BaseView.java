package com.carlosgines.mytimezones.presentation.presenters;

public interface BaseView {

    /**
     * Initialize this view to be ready to render.
     */
    void initView();

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