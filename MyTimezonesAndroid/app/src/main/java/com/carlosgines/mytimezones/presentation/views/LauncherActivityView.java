package com.carlosgines.mytimezones.presentation.views;

import android.support.v7.app.ActionBar;

import com.carlosgines.mytimezones.R;
import com.carlosgines.mytimezones.presentation.presenters.LauncherView;

import javax.inject.Inject;

/**
 * LauncherView implementation using LauncherActivity.
 */
public class LauncherActivityView extends BaseActivityView
        implements LauncherView {

    // ========================================================================
    // Member variables
    // ========================================================================

    /**
     * Base activity used to render views.
     */
    private final LauncherActivity mActivity;

    // ========================================================================
    // Constructor
    // ========================================================================

    @Inject
    public LauncherActivityView(final LauncherActivity activity) {
        super(activity);
        mActivity = activity;
    }

    // ========================================================================
    // LauncherView implementation
    // ========================================================================

    public void initView() {
        mActivity.setContentView(R.layout.activity_launcher);
        ActionBar actionBar = mActivity.getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
    }
}
