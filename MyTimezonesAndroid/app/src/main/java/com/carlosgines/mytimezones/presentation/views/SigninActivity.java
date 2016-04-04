package com.carlosgines.mytimezones.presentation.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.carlosgines.mytimezones.R;
import com.carlosgines.mytimezones.presentation.di.ActivityComponent;
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

    // ==========================================================================
    // Member variables
    // ==========================================================================

    @Inject
    SigninPresenter mPresenter;

    // UI references.
    @Bind(R.id.email)
    EditText mEmailView;
    @Bind(R.id.password)
    EditText mPasswordView;
    @Bind(R.id.login_progress)
    View mProgressView;
    @Bind(R.id.login_form)
    View mLoginFormView;

    /**
     * Dagger component for decisions
     */
    private ActivityComponent mActivityComponent;

    // ==========================================================================
    // Activity lifecycle methods
    // ==========================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        ButterKnife.bind(this);

        // Prepare injector and inject
        this.initInjector();

        // Init event for presenter
        mPresenter.onInit(this);

        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    mPresenter.onSigninClick(mEmailView.getText().toString(),
                            mPasswordView.getText().toString());
                    return true;
                }
                return false;
            }
        });
    }

    // Initializes injector and inject
    private void initInjector() {
        mActivityComponent = DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build();
        mActivityComponent.inject(this);
    }

    // ==========================================================================
    // User input
    // ==========================================================================

    @OnClick(R.id.email_sign_in_button)
    public void onSigninClick() {
        mPresenter.onSigninClick(mEmailView.getText().toString(),
                mPasswordView.getText().toString());
    }

    // ==========================================================================
    // SigninView implementation
    // ==========================================================================

    @Override
    public void showProgress(final boolean show) {
        int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

        mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            }
        });

        mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
        mProgressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }

    @Override
    public void showNoConnection(boolean show) {
        if (show) {
            super.showMessage(R.string.no_connection);
        }
    }

    @Override
    public void resetErrors() {
        mEmailView.setError(null);
        mPasswordView.setError(null);
    }

    @Override
    public void setEmptyEmailError() {
        mEmailView.setError(getString(R.string.error_field_required));
        mEmailView.requestFocus();
    }

    @Override
    public void setInvalidEmailError() {
        mEmailView.setError(getString(R.string.error_invalid_email));
        mEmailView.requestFocus();
    }

    @Override
    public void setEmptyPasswordError() {
        mPasswordView.setError(getString(R.string.error_field_required));
        mPasswordView.requestFocus();
    }
}

