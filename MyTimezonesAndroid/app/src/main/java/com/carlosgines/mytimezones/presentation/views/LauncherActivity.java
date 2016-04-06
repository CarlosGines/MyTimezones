package com.carlosgines.mytimezones.presentation.views;

import android.os.Bundle;
import android.support.v7.app.ActionBar;

import com.carlosgines.mytimezones.R;
import com.carlosgines.mytimezones.presentation.di.DaggerActivityComponent;
import com.carlosgines.mytimezones.presentation.presenters.LauncherPresenter;
import com.carlosgines.mytimezones.presentation.presenters.LauncherView;

import javax.inject.Inject;

/**
 * Launcher activity that redirects according to auth state.
 */
public class LauncherActivity extends BaseActivity implements LauncherView {

    @Inject
    LauncherPresenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);
        initViews();
        initInjector();
        mPresenter.onInit();
    }

    private void initViews() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }

    // Initializes injector and inject
    private void initInjector() {
        DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build().inject(this);
    }

    @Override
    public void showProgress(boolean show) {
    }

    @Override
    public void showNoConnection(boolean show) {
    }
}
