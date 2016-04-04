package com.carlosgines.mytimezones.presentation.presenters;

/**
 * Created by efrel on 4/4/16.
 */
public interface SigninView {

    /**
     * Delete all message errors on fields.
     */
    void resetErrors();

    /**
     * Set empty email error message on email field.
     */
    void setEmptyEmailError();

    /**
     * Set invalid email error message on email field.
     */
    void setInvalidEmailError();

    /**
     * Set empty password error message on password field.
     */
    void setEmptyPasswordError();

    /**
     * Shows the progress UI and hides the login form.
     */
    void showProgress(final boolean show);

    //TODO Remove this method after corresponding Use Case is added
    void startUserLoginTask(String email, String password);
}
