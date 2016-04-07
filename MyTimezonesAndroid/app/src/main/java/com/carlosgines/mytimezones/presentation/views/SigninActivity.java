package com.carlosgines.mytimezones.presentation.views;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.carlosgines.mytimezones.R;
import com.carlosgines.mytimezones.presentation.di.DaggerActivityComponent;
import com.carlosgines.mytimezones.presentation.presenters.SigninPresenter;
import com.carlosgines.mytimezones.presentation.presenters.SigninView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Signin Activity serving as SigninView implementation
 */
public class SigninActivity extends BaseActivity implements SigninView {

    // ========================================================================
    // Member variables
    // ========================================================================

    @Inject
    SigninPresenter mPresenter;

    // UI references.
    @Bind(R.id.username)
    EditText mUserNameView;
    @Bind(R.id.password)
    EditText mPasswordView;
    @Bind(R.id.password2)
    EditText mPassword2View;
    @Bind(R.id.signin_progress)
    View mProgressView;
    @Bind(R.id.signin_form)
    View mContentView;
    @Bind(R.id.action_button)
    Button mActionButton;
    @Bind(R.id.switch_signin_register)
    Button mSwitchButton;

    // ========================================================================
    // Activity lifecycle methods
    // ========================================================================

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);

        this.initViews();
        this.initInjector();
        mPresenter.onInit();
    }

    public void initViews() {
        final TextView.OnEditorActionListener listener =
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int id,
                                                  KeyEvent keyEvent) {
                        if (id == EditorInfo.IME_ACTION_DONE) {
                            SigninActivity.this.onActionClick();
                            return true;
                        }
                        return false;
                    }
                };
        mPasswordView.setOnEditorActionListener(listener);
        mPassword2View.setOnEditorActionListener(listener);
    }

    private void initInjector() {
        DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    // ========================================================================
    // User input
    // ========================================================================

    @OnClick(R.id.action_button)
    public void onActionClick() {
        mPresenter.onActionClick(
                mUserNameView.getText().toString().trim(),
                mPasswordView.getText().toString(),
                mPassword2View.getText().toString()
        );
    }

    @OnClick(R.id.switch_signin_register)
    public void onSwitchClick() {
        mPresenter.onSwitchClick();
    }

    // ========================================================================
    // SigninView implementation
    // ========================================================================

    @Override
    public void showProgress(final boolean show) {
        super.closeKeyboard();
        super.showProgress(show, mProgressView, mContentView);
    }

    @Override
    public void switchViews(final ViewSwitch viewSwitch) {
        mUserNameView.requestFocus();
        if (viewSwitch.equals(ViewSwitch.REGISTER)) {
            setTitle(R.string.title_register);
            mPassword2View.setVisibility(View.VISIBLE);
            mActionButton.setText(R.string.action_register);
            mSwitchButton.setText(R.string.action_switch_to_signin);
        } else {
            setTitle(R.string.title_signin);
            mPassword2View.setVisibility(View.GONE);
            mActionButton.setText(R.string.action_signin);
            mSwitchButton.setText(R.string.action_switch_to_register);
        }
    }

    @Override
    public void resetErrors() {
        mUserNameView.setError(null);
        mPasswordView.setError(null);
    }

    @Override
    public void setEmptyUserNameError() {
        mUserNameView.setError(getString(R.string.error_field_required));
        mUserNameView.requestFocus();
    }

    @Override
    public void setInvalidUserNameError() {
        mUserNameView.setError(getString(R.string.error_invalid_username));
        mUserNameView.requestFocus();
    }

    @Override
    public void setEmptyPasswordError() {
        mPasswordView.setError(getString(R.string.error_field_required));
        mPasswordView.requestFocus();
    }

    @Override
    public void setDifferentPasswordsError() {
        mPasswordView.setError(getString(R.string.error_different_passwords));
        mPasswordView.requestFocus();
    }

    @Override
    public void setInvalidPasswordError() {
        mPasswordView.setError(getString(R.string.error_invalid_password));
        mPasswordView.requestFocus();
    }

    @Override
    public void setAuthFailedError() {
        mPasswordView.setError(getString(R.string.error_auth_failed));
        mPasswordView.requestFocus();
    }

    @Override
    public void setDuplicateUserNameError() {
        mUserNameView.setError(getString(R.string.error_duplicate_username));
        mUserNameView.requestFocus();
    }
}

