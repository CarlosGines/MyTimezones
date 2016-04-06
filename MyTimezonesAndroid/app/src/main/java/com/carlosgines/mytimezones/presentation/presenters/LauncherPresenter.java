package com.carlosgines.mytimezones.presentation.presenters;

import android.text.TextUtils;
import android.util.Log;

import com.carlosgines.mytimezones.domain.usecases.CheckAuthUseCase;
import com.carlosgines.mytimezones.domain.usecases.RegisterUseCase;
import com.carlosgines.mytimezones.domain.usecases.SigninUseCase;
import com.carlosgines.mytimezones.presentation.Navigator;

import javax.inject.Inject;

/**
 * Presenter that controls communication between SigninView views and models.
 */
public class LauncherPresenter {

    // ========================================================================
    // Member variables
    // ========================================================================

    /**
     * View object for events callbacks.
     */
    private final LauncherView mView;

    /**
     * Navigator
     */
    private final Navigator mNavigator;

    // Use cases:
    private final CheckAuthUseCase mCheckAuthUseCase;

    // ========================================================================
    // Constructor
    // ========================================================================

    @Inject
    public LauncherPresenter(final LauncherView view,
                             final Navigator navigator,
                             final CheckAuthUseCase checkAuthUseCase) {
        mView = view;
        mNavigator= navigator;
        mCheckAuthUseCase = checkAuthUseCase;
    }

    // ========================================================================
    // View events
    // ========================================================================

    public void onInit() {
        mCheckAuthUseCase.execute(new CheckAuthSubscriber(mView));
    }

    // ========================================================================
    // Use Case Subscribers
    // ========================================================================

    /**
     * Use case subscriber to receive notifications from CheckAuthSubscriber
     */
    private final class CheckAuthSubscriber
            extends DefaultSubscriber<Boolean> {

        public CheckAuthSubscriber(final BaseView baseView) {
            super(baseView);
        }

        @Override
        public void onNext(final Boolean success) {
            if(success) {
                Log.d(getClass().getSimpleName(), "User found");
                mNavigator.navigateToTzListActivity();
            } else {
                Log.d(getClass().getSimpleName(), "User NOT found");
                mNavigator.navigateToSigninActivity();
            }
        }
    }
}
