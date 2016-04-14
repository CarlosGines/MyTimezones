package com.carlosgines.mytimezones.presentation.presenters;

import android.os.Bundle;

import com.carlosgines.mytimezones.domain.models.Timezone;
import com.carlosgines.mytimezones.domain.models.User;
import com.carlosgines.mytimezones.domain.usecases.DeleteTzUseCase;
import com.carlosgines.mytimezones.domain.usecases.GetTzListUseCase;
import com.carlosgines.mytimezones.domain.usecases.SearchTzUseCase;
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
    private final SearchTzUseCase mSearchTzUseCase;
    private final DeleteTzUseCase mDeleteTzUseCase;

    // View state:
    private List<Timezone> mTzs;
    private User mUser;

    // ========================================================================
    // Constructor
    // ========================================================================

    @Inject
    public TzListPresenter(final TzListView view,
                           final Navigator navigator,
                           final SignoutUseCase signoutUseCase,
                           final GetTzListUseCase tzListUseCase,
                           final SearchTzUseCase tzUseCase,
                           final DeleteTzUseCase deleteTztUseCase) {
        mView = view;
        mNavigator= navigator;
        mSignoutUseCase = signoutUseCase;
        mGetTzListUseCase = tzListUseCase;
        mDeleteTzUseCase = deleteTztUseCase;
        mSearchTzUseCase = tzUseCase;
    }

    // ========================================================================
    // View events
    // ========================================================================

    public void onCreate(final Bundle extras) {
        mUser = (User) extras.getSerializable(Navigator.USER_KEY);
        mView.initView();
        mView.showProgress(true);
        mGetTzListUseCase.execute(new GetTzListSubscriber(mView));
    }

    public void onStart() {
        mView.startTimer();
    }

    public void onStop() {
        mView.stopTimer();
    }

    public void onSignoutClick() {
        mSignoutUseCase.execute(new SignoutSubscriber(mView));
    }

    public void onCreateTzClick() {
        mNavigator.navigateToTzEditActivity(null, mUser);
    }

    public void onItemClick(int pos) {
        mNavigator.navigateToTzEditActivity(mTzs.get(pos), mUser);
    }

    public void onDeleteClick(int pos) {
        mDeleteTzUseCase.execute(mTzs.get(pos), new DeleteTzSubscriber(mView));
    }

    public void onBackFromTzEdit(final Timezone tz) {
        if (tz != null) {
            final int index = mTzs.indexOf(tz);
            if (index == -1) {
                mTzs.add(tz);
            } else {
                mTzs.set(index, tz);
            }
        }
        mView.render(mTzs, mUser.isAdmin());
    }

    public void onQueryTextChange(final String newText) {
        mView.showProgress(true);
        if(newText.isEmpty()) {
            mGetTzListUseCase.execute(new GetTzListSubscriber(mView));
        } else {
            mSearchTzUseCase.execute(newText, new GetTzListSubscriber(mView));
        }
    }

    public void onDestroy() {
        mSignoutUseCase.unsubscribe();
        mGetTzListUseCase.unsubscribe();
        mSearchTzUseCase.unsubscribe();
        mDeleteTzUseCase.unsubscribe();
    }

    // ========================================================================
    // Use Case Subscribers
    // ========================================================================

    /**
     * Use case subscriber to receive notifications from GetTzListSubscriber
     */
    private final class GetTzListSubscriber
            extends DefaultSubscriber<List<Timezone>> {

        public GetTzListSubscriber(final BaseView baseView) {
            super(baseView);
        }

        @Override
        public void onNext(List<Timezone> tzs) {
            final int size = mTzs == null ? 0 : mTzs.size();
            mTzs = tzs;
            if(tzs.size() != size) {
                mView.render(mTzs, mUser.isAdmin());
            }
            mView.showProgress(false);
        }
    }

    /**
     * Use case subscriber to receive notifications from DeleteTzSubscriber
     */
    private final class DeleteTzSubscriber
            extends DefaultSubscriber<Timezone> {

        public DeleteTzSubscriber(final BaseView baseView) {
            super(baseView);
        }

        @Override
        public void onNext(Timezone tz) {
            mTzs.remove(tz);
            mView.showDeleteSuccess();
            mView.render(mTzs, mUser.isAdmin());
        }
    }

    /**
     * Use case subscriber to receive notifications from SignoutSubscriber
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
