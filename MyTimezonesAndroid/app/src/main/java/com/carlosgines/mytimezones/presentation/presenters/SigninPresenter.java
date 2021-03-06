package com.carlosgines.mytimezones.presentation.presenters;

import android.text.TextUtils;

import com.carlosgines.mytimezones.domain.models.User;
import com.carlosgines.mytimezones.domain.usecases.RegisterUseCase;
import com.carlosgines.mytimezones.domain.usecases.SigninUseCase;
import com.carlosgines.mytimezones.presentation.Navigator;

import javax.inject.Inject;

/**
 * Presenter that controls communication between SigninView views and models.
 */
public class SigninPresenter {

    // ========================================================================
    // Member variables
    // ========================================================================

    /**
     * View object for events callbacks.
     */
    private final SigninView mView;

    /**
     * Navigator
     */
    private final Navigator mNavigator;

    // Use cases:
    private final SigninUseCase mSigninUseCase;
    private final RegisterUseCase mRegisterUseCase;

    // View state:
    private SigninView.ViewSwitch mViewSwitch = SigninView.ViewSwitch.REGISTER;


    // ========================================================================
    // Constructor
    // ========================================================================

    @Inject
    public SigninPresenter(final SigninView view,
                           final Navigator navigator,
                           final SigninUseCase signinUseCase,
                           final RegisterUseCase registerUseCase) {
        mView = view;
        mNavigator= navigator;
        mSigninUseCase = signinUseCase;
        mRegisterUseCase = registerUseCase;
    }

    // ========================================================================
    // View events
    // ========================================================================

    public void onCreate() {
        mView.initView();
        this.switchViews();
    }

    public void onActionClick(final String username, final String password,
                              final String password2) {
        if(mViewSwitch.equals(SigninView.ViewSwitch.SIGNIN)) {
            this.attemptSignin(username, password);
        } else {
            this.attemptRegister(username, password, password2);
        }
    }

    public void onSwitchClick() {
        mView.resetErrors();
        this.switchViews();
    }

    // ========================================================================
    // Private methods
    // ========================================================================

    private void switchViews() {
        mViewSwitch = SigninView.ViewSwitch.values()[1- mViewSwitch.ordinal()];
        mView.switchViews(mViewSwitch);
    }

    /**
     * Attempts to sign in the account specified by the form. If there are form
     * errors (invalid username, missing fields, etc.), the errors are
     * presented and sign in is not made.
     */
    private void attemptSignin(final String username, final String password) {
        //TODO Add check of login already in progress
        // Reset errors.
        mView.resetErrors();
        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mView.setEmptyUserNameError();
            return;
        }
        // Check for a valid password, if the user entered one.
        if (TextUtils.isEmpty(password)) {
            mView.setEmptyPasswordError();
            return;
        }
        mView.showProgress(true);
        mSigninUseCase.execute(
                username, password, new SigninSubscriber(mView)
        );
    }

    /**
     * Attempts to register the account specified by the form. If there are
     * form errors (invalid username, missing fields, etc.), the errors are
     * presented and no actual registration is made.
     */
    private void attemptRegister(final String username, final String password,
                                 final String password2) {
        //TODO Add check of login already in progress
        // Reset errors.
        mView.resetErrors();
        // Check for a valid username.
        if (TextUtils.isEmpty(username)) {
            mView.setEmptyUserNameError();
            return;
        }
        if (!isUserNameValid(username)) {
            mView.setInvalidUserNameError();
            return;
        }
        // Check for a valid password
        if (TextUtils.isEmpty(password)) {
            mView.setEmptyPasswordError();
            return;
        }
        if (!password.equals(password2)) {
            mView.setDifferentPasswordsError();
            return;
        }
        if (!isPasswordValid(password)) {
            mView.setInvalidPasswordError();
            return;
        }
        mView.showProgress(true);
        mRegisterUseCase.execute(
                username, password, new RegisterSubscriber(mView)
        );
    }

    private boolean isUserNameValid(final String userName) {
        return userName.length() >= 4;
    }

    private boolean isPasswordValid(final String password) {
        return password.length() >= 4;
    }

    public void onDestroy() {
        mSigninUseCase.unsubscribe();
        mRegisterUseCase.unsubscribe();
    }

    // ========================================================================
    // Use Case Subscribers
    // ========================================================================

    /**
     * Use case subscriber to receive notifications from SigninUseCase
     */
    private final class SigninSubscriber extends DefaultSubscriber<User> {

        public SigninSubscriber(final BaseView baseView) {
            super(baseView);
        }

        @Override
        public void onNext(final User user) {
            if(user != null) {
                mNavigator.navigateToTzListActivity(user);
            } else {
                mView.showProgress(false);
                mView.setAuthFailedError();
            }
        }
    }

    /**
     * Use case subscriber to receive notifications from RegisterSubscriber
     */
    private final class RegisterSubscriber extends DefaultSubscriber<User> {

        public RegisterSubscriber(final BaseView baseView) {
            super(baseView);
        }

        @Override
        public void onNext(final User user) {
            if(user != null) {
                mNavigator.navigateToTzListActivity(user);
            } else {
                mView.showProgress(false);
                mView.setDuplicateUserNameError();
            }
        }
    }
}
