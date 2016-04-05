package com.carlosgines.mytimezones.presentation.presenters;

import android.text.TextUtils;
import android.view.View;

import com.carlosgines.mytimezones.domain.usecases.SigninUseCase;

import javax.inject.Inject;

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

    // Use cases
    private final SigninUseCase mSigninUseCase;

    // ==========================================================================
    // Constructor
    // ==========================================================================

    @Inject
    public SigninPresenter(SigninUseCase signinUseCase) {
        mSigninUseCase = signinUseCase;
    }

    // ==========================================================================
    // View events
    // ==========================================================================

    public void onInit(SigninView view) {
        // Set view
        mView = view;
    }

    public void onSigninClick(String username, String password) {
        attemptLogin(username, password);
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid username, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin(final String username, final String password) {
        //TODO Add check of login already in progress

        // Reset errors.
        mView.resetErrors();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid username address.
        if (TextUtils.isEmpty(username)) {
            mView.setEmptyEmailError();
            return;
        }
        if (!isUserNameValid(username)) {
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

        mSigninUseCase.execute(username, password, new SigninSubscriber(mView));
    }

    private boolean isUserNameValid(String email) {
        //TODO: Replace this with your own logic
        return email.contains("");
    }

    private boolean isPasswordValid(String password) {
        //TODO: Replace this with your own logic
        return password.length() > 4;
    }

    // ==========================================================================
    // Use Case Subscribers
    // ==========================================================================

    /**
     * Use case subscriber to receive notifications from GetDecDetUseCase and PreviewDecDetUseCase
     */
    private final class SigninSubscriber extends DefaultSubscriber<String> {

        public SigninSubscriber(BaseView baseView) {
            super(baseView);
        }

        @Override
        public void onNext(final String token) {
            mView.showProgress(false);
            if(TextUtils.isEmpty(token)) {
                mView.showMessage("You failed.");
            } else {
                mView.showMessage("Signed in! Token: " + token);
            }
        }

        @Override
        public void onError() {
            mView.showProgress(false);
        }
    }
}
