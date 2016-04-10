package com.carlosgines.mytimezones.presentation;

import android.app.Activity;
import android.content.Intent;

import com.carlosgines.mytimezones.domain.models.Timezone;
import com.carlosgines.mytimezones.domain.models.User;
import com.carlosgines.mytimezones.presentation.di.PerActivity;
import com.carlosgines.mytimezones.presentation.views.SigninActivity;
import com.carlosgines.mytimezones.presentation.views.TzEditActivity;
import com.carlosgines.mytimezones.presentation.views.TzListActivity;

import javax.inject.Inject;

/**
 * Class used to navigate through the application.
 */
@PerActivity
public class Navigator {

    /**
     * Default request code.
     */
    public static final int DEFAULT_RC = 0;
    public static final String TZ_KEY = "tz";
    public static final String USER_KEY = "user";

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
        i.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK
        );
        mActivity.startActivity(i);
    }

    public void navigateToTzListActivity(final User user) {
        Intent i = new Intent(mActivity, TzListActivity.class);
        i.putExtra(USER_KEY, user);
        i.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK
        );
        mActivity.startActivity(i);
    }

    public void navigateToTzEditActivity(final Timezone tz, final User user) {
        Intent i = new Intent(mActivity, TzEditActivity.class);
        i.putExtra(USER_KEY, user);
        i.putExtra(TZ_KEY, tz);
        mActivity.startActivityForResult(i, DEFAULT_RC);
    }

    public void navigateBackFromTzEditActivity(final Timezone tz) {
        mActivity.setResult(
                Activity.RESULT_OK, new Intent().putExtra(TZ_KEY, tz)
        );
        mActivity.finish();
    }
}