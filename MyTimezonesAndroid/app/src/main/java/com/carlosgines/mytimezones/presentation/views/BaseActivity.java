package com.carlosgines.mytimezones.presentation.views;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.carlosgines.mytimezones.BuildConfig;
import com.carlosgines.mytimezones.R;
import com.carlosgines.mytimezones.presentation.di.ActivityModule;
import com.carlosgines.mytimezones.presentation.di.ApplicationComponent;
import com.carlosgines.mytimezones.presentation.di.MyTimezonesApplication;
import com.carlosgines.mytimezones.presentation.presenters.BaseView;

/**
 * Base Activity class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity
        implements BaseView {

    // ==========================================================================
    // Activity lifecycle methods
    // ==========================================================================

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    // ==========================================================================
    // BaseView implementation
    // ==========================================================================

    @Override
    public void showProgress(final boolean show) {
    }

    @Override
    public void showNoConnection(final boolean show) {
        if (show) {
            showMessage(R.string.no_connection);
        }
    }

    @Override
    public void showMessage(final String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(final Throwable e) {
        // Throwable cause = e.getCause();
        if (BuildConfig.DEBUG) {
            // Custom RuntimeExceptions must have explicit debug message set as the detail message.
            showMessage("Debug: " + e.getMessage());
        } else {
            showMessage(R.string.unexpected_error);
        }
    }

    // ==========================================================================
    // Public methods
    // ==========================================================================

    public void showMessage(final int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    // ==========================================================================
    // Helper methods
    // ==========================================================================

    public void showProgress(final boolean show, final View progressView,
                             final View contentView) {
        final int shortAnimTime = getResources()
                .getInteger(android.R.integer.config_shortAnimTime);
        contentView.setVisibility(show ? View.GONE : View.VISIBLE);
        contentView.animate().setDuration(shortAnimTime).alpha(
                show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                contentView.setVisibility(
                        show ? View.GONE : View.VISIBLE
                );
            }
        });
        progressView.setVisibility(show ? View.VISIBLE : View.GONE);
        progressView.animate().setDuration(shortAnimTime).alpha(
                show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                progressView.setVisibility(show ? View.VISIBLE : View.GONE);
            }
        });
    }
    protected void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            ((InputMethodManager)getSystemService(INPUT_METHOD_SERVICE))
                    .hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Get the Main Application component for dependency injection.
     * @return {@link ApplicationComponent}
     */
    protected ApplicationComponent getApplicationComponent() {
        return ((MyTimezonesApplication) getApplication()).getApplicationComponent();
    }

    /**
     * Get an Activity module for dependency injection.
     * @return ActivityModule
     */
    protected ActivityModule getActivityModule() {
        return new ActivityModule(this);
    }
}
