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
public abstract class BaseActivity extends AppCompatActivity {

    // ==========================================================================
    // Activity lifecycle methods
    // ==========================================================================

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getApplicationComponent().inject(this);
    }

    // ==========================================================================
    // Helper methods
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
