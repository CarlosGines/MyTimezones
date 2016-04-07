package com.carlosgines.mytimezones.presentation.presenters;

import android.text.TextUtils;

import com.carlosgines.mytimezones.domain.models.Timezone;
import com.carlosgines.mytimezones.domain.usecases.CreateTzUseCase;
import com.carlosgines.mytimezones.presentation.Navigator;

import javax.inject.Inject;

/**
 * Presenter that controls communication between TzEditView views and models.
 */
public class TzEditPresenter {

    // ========================================================================
    // Member variables
    // ========================================================================

    /**
     * View object for events callbacks.
     */
    private final TzEditView mView;

    /**
     * Navigator
     */
    private final Navigator mNavigator;

    // Use cases:
    private final CreateTzUseCase mCreateTzUseCase;

    // View state:
    private TzEditView.ViewMode mMode;
    private Timezone mTz;

    // ========================================================================
    // Constructor
    // ========================================================================

    @Inject
    public TzEditPresenter(final TzEditView view,
                           final Navigator navigator,
                           final CreateTzUseCase createTzUseCase) {
        mView = view;
        mNavigator = navigator;
        mCreateTzUseCase = createTzUseCase;
    }

    // ========================================================================
    // View events
    // ========================================================================

    public void onInit() {
        mMode = TzEditView.ViewMode.CREATE;
        mView.setViewMode(mMode, null);
    }

    public void onActionClick(final String name, final String city,
                              final String timeDiff) {
        if (mMode.equals(TzEditView.ViewMode.CREATE)) {
            this.attemptCreate(name, city, timeDiff);
        }
    }

    public void onBackPressed() {
        mNavigator.navigateBackFromTzEditActivity(mTz);
    }

    // ========================================================================
    // Private methods
    // ========================================================================

    /**
     * Attempts to create the timezone record specified by the form. If there
     * are form errors, they are presented and creation is not made.
     */
    private void attemptCreate(final String name, final String city,
                               final String timeDiff) {
        //TODO Add check of action already in progress
        mView.resetErrors();
        if (TextUtils.isEmpty(name)) {
            mView.setEmptyNameError();
            return;
        }
        if (TextUtils.isEmpty(city)) {
            mView.setEmptyCityError();
            return;
        }
        if (TextUtils.isEmpty(timeDiff)) {
            mView.setEmptyTimeDiffError();
            return;
        }
        final int timeDiffNum = Integer.parseInt(timeDiff);
        if (!isTimeDiffValid(timeDiffNum)) {
            mView.setInvalidTimeDiffError();
            return;
        }
        mView.showProgress(true);
        mCreateTzUseCase.execute(
                new Timezone(name, city, timeDiffNum),
                new CreateTzSubscriber(mView)
        );
    }

    private boolean isTimeDiffValid(final int timeDiff) {
        return Math.abs(timeDiff) < 12;
    }

    // ========================================================================
    // Use Case Subscribers
    // ========================================================================

    /**
     * Use case subscriber to receive notifications from CreateTzUseCase
     */
    private final class CreateTzSubscriber
            extends DefaultSubscriber<Timezone> {

        public CreateTzSubscriber(final BaseView baseView) {
            super(baseView);
        }

        @Override
        public void onNext(final Timezone tz) {
            mTz = tz;
            mView.showProgress(false);
            mView.showCreationSuccess();
            mView.setViewMode(TzEditView.ViewMode.EDIT, mTz);
        }

        @Override
        public void onError() {
            mView.showProgress(false);
        }
    }
}
