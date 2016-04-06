package com.carlosgines.mytimezones.presentation;

import android.app.Activity;
import android.content.Intent;

import com.carlosgines.mytimezones.presentation.di.PerActivity;
import com.carlosgines.mytimezones.presentation.views.TzListActivity;

import javax.inject.Inject;

/**
 * Class used to navigate through the application.
 */
@PerActivity
public class Navigator {

    private final Activity mActivity;

    @Inject
    public Navigator(Activity activity) {
        mActivity = activity;
    }

    public void navigateBack() {
        mActivity.finish();
    }

    public void navigateToTzListActivity() {
        mActivity.startActivity(new Intent(mActivity, TzListActivity.class));
    }
}