package com.carlosgines.mytimezones.presentation.presenters;

import android.os.Bundle;
import android.text.TextUtils;

import com.carlosgines.mytimezones.domain.models.Timezone;
import com.carlosgines.mytimezones.domain.usecases.CreateTzUseCase;
import com.carlosgines.mytimezones.domain.usecases.EditTzUseCase;
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
    private final EditTzUseCase mEditTzUseCase;

    // View state:
    private TzEditView.ViewMode mMode;
    private Timezone mTz;

    // ========================================================================
    // Constructor
    // ========================================================================

    @Inject
    public TzEditPresenter(final TzEditView view,
                           final Navigator navigator,
                           final CreateTzUseCase createTzUseCase,
                           final EditTzUseCase editTzUseCase) {
        mView = view;
        mNavigator = navigator;
        mCreateTzUseCase = createTzUseCase;
        mEditTzUseCase = editTzUseCase;
    }

    // ========================================================================
    // View events
    // ========================================================================

    public void onInit(Bundle extras) {
        mTz = (Timezone) extras.getSerializable(Navigator.TZ_KEY);
        mMode = mTz == null ? TzEditView.ViewMode.CREATE :
                TzEditView.ViewMode.EDIT;
        mView.setViewMode(mMode, mTz);
    }

    public void onActionClick(final String name, final String city,
                              final String timeDiff) {
        if (mMode.equals(TzEditView.ViewMode.CREATE)) {
            this.attemptCreate(name, city, timeDiff);
        } else {
            this.attemptEdit(name, city, timeDiff);
        }
    }

    public void onBackPressed() {
        mNavigator.navigateBackFromTzEditActivity(mTz);
    }

    public void onDestroy() {
        mCreateTzUseCase.unsubscribe();
        mEditTzUseCase.unsubscribe();
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
        if (!this.checkFields(name, city, timeDiff)) {
            return;
        }
        mView.showProgress(true);
        mCreateTzUseCase.execute(
                new Timezone(name, city, Integer.parseInt(timeDiff)),
                new EditTzSubscriber(mView)
        );
    }

    /**
     * Attempts to create the timezone record specified by the form. If there
     * are form errors, they are presented and creation is not made.
     */
    private void attemptEdit(final String name, final String city,
                             final String timeDiff) {
        //TODO Add check of action already in progress
        if (!this.checkFields(name, city, timeDiff)) {
            return;
        }
        final int timeDiffNum = Integer.parseInt(timeDiff);
        if (!name.equals(mTz.getName()) || !city.equals(mTz.getCity()) ||
                timeDiffNum != mTz.getTimeDiff()) {
            mView.showProgress(true);
            mTz.setName(name);
            mTz.setCity(city);
            mTz.setTimeDiff(timeDiffNum);
            mEditTzUseCase.execute(mTz, new EditTzSubscriber(mView));
        } else {
            mView.showEditSuccess(mMode);
        }
    }

    private boolean checkFields(final String name, final String city,
                                final String timeDiff) {
        mView.resetErrors();
        if (TextUtils.isEmpty(name)) {
            mView.setEmptyNameError();
            return false;
        }
        if (TextUtils.isEmpty(city)) {
            mView.setEmptyCityError();
            return false;
        }
        if (TextUtils.isEmpty(timeDiff)) {
            mView.setEmptyTimeDiffError();
            return false;
        }
        if (!isTimeDiffValid(timeDiff)) {
            mView.setInvalidTimeDiffError();
            return false;
        }
        return true;
    }

    private boolean isTimeDiffValid(final String timeDiff) {
        return Math.abs(Integer.parseInt(timeDiff)) < 12;
    }

    // ========================================================================
    // Use Case Subscribers
    // ========================================================================

    /**
     * Use case subscriber to receive notifications from CreateTzUseCase
     */
    private final class EditTzSubscriber
            extends DefaultSubscriber<Timezone> {

        public EditTzSubscriber(final BaseView baseView) {
            super(baseView);
        }

        @Override
        public void onNext(final Timezone tz) {
            mTz = tz;
            mView.showProgress(false);
            mView.showEditSuccess(mMode);
            if (mMode.equals(TzEditView.ViewMode.CREATE)) {
                mView.setViewMode(TzEditView.ViewMode.EDIT, mTz);
            }
        }

        @Override
        public void onError() {
            mView.showProgress(false);
        }
    }
}
