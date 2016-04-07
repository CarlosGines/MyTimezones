package com.carlosgines.mytimezones.presentation.di;

import android.app.Activity;

import com.carlosgines.mytimezones.presentation.presenters.LauncherView;
import com.carlosgines.mytimezones.presentation.presenters.SigninView;
import com.carlosgines.mytimezones.presentation.presenters.TzEditView;
import com.carlosgines.mytimezones.presentation.presenters.TzListView;

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
    LauncherView provideLauncherView() {
        return (LauncherView)this.activity;
    }

    @Provides
    @PerActivity
    SigninView provideSigninView() {
        return (SigninView)this.activity;
    }

    @Provides
    @PerActivity
    TzListView provideTzListView() {
        return (TzListView)this.activity;
    }

    @Provides
    @PerActivity
    TzEditView provideTzEditView() {
        return (TzEditView)this.activity;
    }
}
