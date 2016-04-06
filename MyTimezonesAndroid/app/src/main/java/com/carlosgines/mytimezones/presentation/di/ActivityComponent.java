package com.carlosgines.mytimezones.presentation.di;

import android.app.Activity;

import com.carlosgines.mytimezones.presentation.views.LauncherActivity;
import com.carlosgines.mytimezones.presentation.views.SigninActivity;

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

    //Exposed to sub-graphs.
    Activity activity();
}