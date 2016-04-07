package com.carlosgines.mytimezones.presentation.presenters;

/**
 * View for Sign in screen
 */
public interface SigninView extends BaseView{

    /**
     * Enum to indicate view state that can be switched to register view or
     * signin view
     */
    enum ViewSwitch {SIGNIN, REGISTER};

    /**
     * Switch between signin and register views modes.
     * @param viewSwitch new view to switch to.
     */
    void switchViews(ViewSwitch viewSwitch);

    /**
     * Delete all error messages on fields.
     */
    void resetErrors();

    /**
     * Set empty error message on user name field.
     */
    void setEmptyUserNameError();

    /**
     * Set invalid error message on user name field.
     */
    void setInvalidUserNameError();

    /**
     * Set empty error message on password field.
     */
    void setEmptyPasswordError();

    /**
     * Set error message on password fields when they do not match.
     */
    void setDifferentPasswordsError();

    /**
     * Set invalid error message on password field.
     */
    void setInvalidPasswordError();

    /**
     * Set authentication failure error message on password field.
     */
    void setAuthFailedError();

    /**
     * Set duplicate user name error message on user name field.
     */
    void setDuplicateUserNameError();

}
