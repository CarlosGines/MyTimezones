package com.carlosgines.mytimezones.presentation.presenters;

import com.carlosgines.mytimezones.domain.models.Timezone;
import com.carlosgines.mytimezones.domain.usecases.CreateTzUseCase;
import com.carlosgines.mytimezones.domain.usecases.GetTzListUseCase;
import com.carlosgines.mytimezones.domain.usecases.SignoutUseCase;
import com.carlosgines.mytimezones.presentation.Navigator;

import java.util.List;

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
    private final GetTzListUseCase mGetTzListUseCase;
    private final CreateTzUseCase mCreateTzUseCase;

    // ========================================================================
    // Constructor
    // ========================================================================

    @Inject
    public TzListPresenter(final TzListView view,
                           final Navigator navigator,
                           final SignoutUseCase signoutUseCase,
                           final GetTzListUseCase tzListUseCase,
                           final CreateTzUseCase createTzUseCase) {
        mView = view;
        mNavigator= navigator;
        mSignoutUseCase = signoutUseCase;
        mGetTzListUseCase = tzListUseCase;
        mCreateTzUseCase = createTzUseCase;
    }

    // ========================================================================
    // View events
    // ========================================================================

    public void onInit() {
        mGetTzListUseCase.execute(new GetTzListSubscriber(mView));
    }

    public void onSignoutClick() {
        mSignoutUseCase.execute(new SignoutSubscriber(mView));
    }

    public void onCreateTzClick() {
        mNavigator.navigateToTzEditActivity();
    }

    // ========================================================================
    // Use Case Subscribers
    // ========================================================================

    /**
     * Use case subscriber to receive notifications from CheckAuthSubscriber
     */
    private final class GetTzListSubscriber
            extends DefaultSubscriber<List<Timezone>> {

        public GetTzListSubscriber(final BaseView baseView) {
            super(baseView);
        }

        @Override
        public void onNext(List<Timezone> timezones) {
            mView.render(timezones);
        }
    }

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
