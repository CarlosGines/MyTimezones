package com.carlosgines.mytimezones.presentation.presenters;

import android.util.Log;

import com.carlosgines.mytimezones.domain.usecases.CheckAuthUseCase;
import com.carlosgines.mytimezones.domain.usecases.SignoutUseCase;
import com.carlosgines.mytimezones.presentation.Navigator;

import javax.inject.Inject;

/**
 * Presenter that controls communication between SigninView views and models.
 */
public class TzListPresenter {

    // ========================================================================
    // Member variables
    // ========================================================================

    /**
     * View object for events callbacks.
     */
    private final TzListView mView;

    /**
     * Navigator
     */
    private final Navigator mNavigator;

    // Use cases:
    private final SignoutUseCase mSignoutUseCase;

    // ========================================================================
    // Constructor
    // ========================================================================

    @Inject
    public TzListPresenter(final TzListView view,
                           final Navigator navigator,
                           final SignoutUseCase signoutUseCase) {
        mView = view;
        mNavigator= navigator;
        mSignoutUseCase = signoutUseCase;
    }

    // ========================================================================
    // View events
    // ========================================================================

    public void onInit() {

    }

    public void onSignoutClick() {
        mSignoutUseCase.execute(new SignoutSubscriber(mView));
    }

    // ========================================================================
    // Use Case Subscribers
    // ========================================================================

    /**
     * Use case subscriber to receive notifications from CheckAuthSubscriber
     */
    private final class SignoutSubscriber
            extends DefaultSubscriber<Boolean> {

        public SignoutSubscriber(final BaseView baseView) {
            super(baseView);
        }

        @Override
        public void onCompleted() {
            mNavigator.navigateToSigninActivity();
        }
    }
}
