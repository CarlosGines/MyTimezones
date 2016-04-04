package com.carlosgines.mytimezones.presentation.di;

import android.content.Context;

import com.carlosgines.mytimezones.domain.repositories.UserRepository;
import com.carlosgines.mytimezones.domain.usecases.rx.PostExecutionThread;
import com.carlosgines.mytimezones.domain.usecases.rx.ThreadExecutor;
import com.carlosgines.mytimezones.presentation.views.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * A component whose lifetime is the life of the application.
 */
@Singleton // Constraints this component to one-per-application or unscoped bindings.
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity baseActivity);

    //Exposed to sub-graphs.
    Context context();
    ThreadExecutor threadExecutor();
    PostExecutionThread postExecutionThread();

    // Repositories
    UserRepository userRepository();
}

