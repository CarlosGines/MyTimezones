package com.carlosgines.mytimezones.presentation.views;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.carlosgines.mytimezones.presentation.di.ActivityModule;
import com.carlosgines.mytimezones.presentation.di.ApplicationComponent;
import com.carlosgines.mytimezones.presentation.di.MyTimezonesApplication;

/**
 * Base Activity class for every Activity in this application.
 */
public abstract class BaseActivity extends AppCompatActivity {

    // ==========================================================================
    // Activity lifecycle methods
    // ==========================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
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
