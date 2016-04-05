package com.carlosgines.mytimezones.presentation.presenters;

/**
 * Created by efrel on 4/4/16.
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
     * Delete all message errors on fields.
     */
    void resetErrors();

    /**
     * Set empty email error message on email field.
     */
    void setEmptyUserNameError();

    /**
     * Set invalid email error message on email field.
     */
    void setInvalidUserNameError();

    /**
     * Set empty password error message on password field.
     */
    void setEmptyPasswordError();

    /**
     * Set error message on password fields when they do not match.
     */
    void setDifferentPasswordsError();

    /**
     * Set invalid password error message on password field.
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
