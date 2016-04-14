package com.carlosgines.mytimezones.presentation.views;

import android.os.Bundle;

import com.carlosgines.mytimezones.presentation.di.DaggerActivityComponent;
import com.carlosgines.mytimezones.presentation.presenters.LauncherPresenter;

import javax.inject.Inject;

/**
 * Launcher activity that redirects according to auth state.
 */
public class LauncherActivity extends BaseActivity{

    @Inject
    LauncherPresenter mPresenter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initInjector();
        mPresenter.onCreate();
    }

    // Initializes injector and inject
    private void initInjector() {
        DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build().inject(this);
    }
}
