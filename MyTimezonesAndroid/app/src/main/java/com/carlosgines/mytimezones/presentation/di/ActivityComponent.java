package com.carlosgines.mytimezones.presentation.di;

import android.app.Activity;

import com.carlosgines.mytimezones.presentation.views.LauncherActivity;
import com.carlosgines.mytimezones.presentation.views.SigninActivity;
import com.carlosgines.mytimezones.presentation.views.TzEditActivity;
import com.carlosgines.mytimezones.presentation.views.TzListActivity;

import dagger.Component;

/**
 * A base component upon which fragments' components may depend.
 * Activity-level components should extend this component.
 *
 * Subtypes of ActivityComponent should be decorated with annotation @PerActivity
 */
@PerActivity
@Component(dependencies = ApplicationComponent.class, modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(LauncherActivity launcherActivity);
    void inject(SigninActivity signinActivity);
    void inject(TzListActivity tzListActivity);
    void inject(TzEditActivity tzEditActivity);

    //Exposed to sub-graphs.
    Activity activity();
}