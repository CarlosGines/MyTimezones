package com.carlosgines.mytimezones.presentation.presenters;

import android.util.Log;

import com.carlosgines.mytimezones.domain.models.User;
import com.carlosgines.mytimezones.domain.usecases.GetAuthUserCase;
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
    private final GetAuthUserCase mGetAuthUserCase;

    // ========================================================================
    // Constructor
    // ========================================================================

    @Inject
    public LauncherPresenter(final LauncherView view,
                             final Navigator navigator,
                             final GetAuthUserCase getAuthUserCase) {
        mView = view;
        mNavigator= navigator;
        mGetAuthUserCase = getAuthUserCase;
    }

    // ========================================================================
    // View events
    // ========================================================================

    public void onCreate() {
        mView.initView();
        mGetAuthUserCase.execute(new GetAuthSubscriber(mView));
    }

    // ========================================================================
    // Use Case Subscribers
    // ========================================================================

    /**
     * Use case subscriber to receive notifications from CheckAuthSubscriber
     */
    private final class GetAuthSubscriber
            extends DefaultSubscriber<User> {

        public GetAuthSubscriber(final BaseView baseView) {
            super(baseView);
        }

        @Override
        public void onNext(final User user) {
            if(user != null) {
                Log.d(getClass().getSimpleName(), "User found");
                mNavigator.navigateToTzListActivity(user);
            } else {
                Log.d(getClass().getSimpleName(), "User NOT found");
                mNavigator.navigateToSigninActivity();
            }
        }
    }
}
