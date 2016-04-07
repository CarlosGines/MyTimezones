package com.carlosgines.mytimezones.presentation.presenters;

import com.carlosgines.mytimezones.domain.models.Timezone;
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

    // View state:
    private List<Timezone> mTzs;

    // ========================================================================
    // Constructor
    // ========================================================================

    @Inject
    public TzListPresenter(final TzListView view,
                           final Navigator navigator,
                           final SignoutUseCase signoutUseCase,
                           final GetTzListUseCase tzListUseCase) {
        mView = view;
        mNavigator= navigator;
        mSignoutUseCase = signoutUseCase;
        mGetTzListUseCase = tzListUseCase;
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
        mNavigator.navigateToTzEditActivity(null);
    }

    public void onBackFromTzEdit(final Timezone tz) {
        final int index = mTzs.indexOf(tz);
        if(index == -1) {
            mTzs.add(tz);
        } else {
            mTzs.set(index, tz);
        }
        mView.render(mTzs);
    }

    public void onDestroy() {
        mSignoutUseCase.unsubscribe();
        mGetTzListUseCase.unsubscribe();
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
        public void onNext(List<Timezone> tzs) {
            mTzs = tzs;
            mView.render(mTzs);
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
