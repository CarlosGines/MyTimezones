package com.carlosgines.mytimezones.presentation;

import android.app.Activity;
import android.content.Intent;

import com.carlosgines.mytimezones.presentation.di.PerActivity;
import com.carlosgines.mytimezones.presentation.presenters.SigninView;
import com.carlosgines.mytimezones.presentation.views.SigninActivity;
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

    public void navigateToSigninActivity() {
        Intent i = new Intent(mActivity, SigninActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(i);
    }

    public void navigateToTzListActivity() {
        Intent i = new Intent(mActivity, TzListActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        mActivity.startActivity(i);
    }
}