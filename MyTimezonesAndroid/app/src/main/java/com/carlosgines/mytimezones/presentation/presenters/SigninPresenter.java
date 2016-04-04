package com.carlosgines.mytimezones.presentation.presenters;

import android.text.TextUtils;
import android.view.View;

/**
 * Presenter that controls communication between SigninView views and models.
 */
public class SigninPresenter {

    // ==========================================================================
    // Member variables
    // ==========================================================================

    /**
     * View object for events callbacks
     */
    private SigninView mView;

    // ==========================================================================
    // View events
    // ==========================================================================

    public void onInit(SigninView view) {
        // Set view
        mView = view;
    }

    public void onSigninClick(String email, String password) {
        attemptLogin(email, password);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin(final String email, final String password) {
        //TODO Add check of login already in progress

        // Reset errors.
        mView.resetErrors();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mView.setEmptyEmailError();
            return;
        }
        if (!isEmailValid(email)) {
            mView.setInvalidEmailError();
            return;
        }
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mView.setEmptyPasswordError();
            return;
        }

        // Show a progress spinner, and kick off a background task to
        // perform the user login attempt.
        mView.showProgress(true);
        mView.startUserLoginTask(email, password);
    }

    private boolean isEmailValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }
}
