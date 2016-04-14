package com.carlosgines.mytimezones.presentation.di;

import android.app.Activity;

import com.carlosgines.mytimezones.presentation.presenters.LauncherView;
import com.carlosgines.mytimezones.presentation.presenters.SigninView;
import com.carlosgines.mytimezones.presentation.presenters.TzEditView;
import com.carlosgines.mytimezones.presentation.presenters.TzListView;
import com.carlosgines.mytimezones.presentation.views.LauncherActivity;
import com.carlosgines.mytimezones.presentation.views.LauncherActivityView;
import com.carlosgines.mytimezones.presentation.views.SigninActivity;
import com.carlosgines.mytimezones.presentation.views.SigninActivityView;
import com.carlosgines.mytimezones.presentation.views.TzEditActivity;
import com.carlosgines.mytimezones.presentation.views.TzEditActivityView;
import com.carlosgines.mytimezones.presentation.views.TzListActivity;
import com.carlosgines.mytimezones.presentation.views.TzListActivityView;

import dagger.Module;
import dagger.Provides;

/**
 * A module to wrap the Activity state and expose it to the graph.
 */
@Module
public class ActivityModule {

    private final Activity activity;

    public ActivityModule(Activity activity) {
        this.activity = activity;
    }

    /**
     * Expose the activity to dependents in the graph.
     */
    @Provides
    @PerActivity
    Activity provideActivity() {
        return this.activity;
    }

    @Provides
    @PerActivity
    LauncherActivity provideLauncherActivity() {
        return (LauncherActivity) activity;
    }

    @Provides
    @PerActivity
    LauncherView provideLauncherView(final LauncherActivityView view) {
        return view;
    }

    @Provides
    @PerActivity
    SigninActivity provideSigninActivity() {
        return (SigninActivity) activity;
    }

    @Provides
    @PerActivity
    SigninView provideSigninView(final SigninActivityView view) {
        return view;
    }

    @Provides
    @PerActivity
    TzListActivity provideTzListActivity() {
        return (TzListActivity) activity;
    }

    @Provides
    @PerActivity
    TzListView provideTzListView(final TzListActivityView view) {
        return view;
    }

    @Provides
    @PerActivity
    TzEditActivity provideTzEditActivity() {
        return (TzEditActivity) activity;
    }

    @Provides
    @PerActivity
    TzEditView provideTzEditView(final TzEditActivityView view) {
        return view;
    }
}
