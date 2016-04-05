package com.carlosgines.mytimezones.presentation.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.carlosgines.mytimezones.BuildConfig;
import com.carlosgines.mytimezones.R;
import com.carlosgines.mytimezones.presentation.di.ActivityModule;
import com.carlosgines.mytimezones.presentation.di.ApplicationComponent;
import com.carlosgines.mytimezones.presentation.di.MyTimezonesApplication;
import com.carlosgines.mytimezones.presentation.presenters.BaseView;

import org.json.JSONException;

/**
 * Base Activity class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseView {

    // ==========================================================================
    // Activity lifecycle methods
    // ==========================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    // ==========================================================================
    // BaseView implementation
    // ==========================================================================

    @Override
    public void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorMessage(Throwable e) {
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

    public void showMessage(int resId) {
        Toast.makeText(this, resId, Toast.LENGTH_SHORT).show();
    }

    // ==========================================================================
    // Injection helper methods
    // ==========================================================================

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
