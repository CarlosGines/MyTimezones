package com.carlosgines.mytimezones.presentation.di;

import android.content.Context;

import com.carlosgines.mytimezones.data.repositories.UserDataRepository;
import com.carlosgines.mytimezones.data.JobExecutor;
import com.carlosgines.mytimezones.domain.repositories.UserRepository;
import com.carlosgines.mytimezones.domain.usecases.rx.PostExecutionThread;
import com.carlosgines.mytimezones.domain.usecases.rx.ThreadExecutor;
import com.carlosgines.mytimezones.presentation.UIThread;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Dagger module that provides objects which will live during the mApplication lifecycle.
 */
@Module
public class ApplicationModule {

    private final Context mApplicationCtx;

    public ApplicationModule(Context applicationCtx) {
        mApplicationCtx = applicationCtx;
    }

    @Provides
    @Singleton
    Context provideApplicationContext() {
        return mApplicationCtx;
    }

    @Provides
    @Singleton
    ThreadExecutor provideThreadExecutor(JobExecutor jobExecutor) {
        return jobExecutor;
    }

    @Provides
    @Singleton
    PostExecutionThread providePostExecutionThread(UIThread uiThread) {
        return uiThread;
    }

    @Provides
    @Singleton
    UserRepository provideConRepository(UserDataRepository userDataRepository) {
        return userDataRepository;
    }
}