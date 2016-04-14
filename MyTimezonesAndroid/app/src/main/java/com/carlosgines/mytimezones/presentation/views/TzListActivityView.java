package com.carlosgines.mytimezones.presentation.views;

import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.ListView;

import com.carlosgines.mytimezones.R;
import com.carlosgines.mytimezones.domain.models.Timezone;
import com.carlosgines.mytimezones.presentation.presenters.TzListView;
import com.carlosgines.mytimezones.presentation.views.adapters.TzListViewAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

/**
 * TzListView implementation using TzListActivity.
 */
public class TzListActivityView extends BaseActivityView
        implements TzListView {

    // ========================================================================
    // Member variables
    // ========================================================================

    @Bind(android.R.id.list)
    ListView mTzListView;
    @Bind(R.id.progress)
    View mProgressView;
    @Bind(R.id.main_content)
    View mContentView;

    /**
     * Base activity used to render views.
     */
    private final TzListActivity mActivity;

    /**
     * Adapter of the timezones list view.
     */
    private TzListViewAdapter mAdapter;


    /**
     * Timer to update timezones current time.
     */
    private final CountDownTimer mTimer = new CountDownTimer(100000000, 1000) {

        public void onTick(final long millisUntilFinished) {
            if (mAdapter != null && mAdapter.getCount() > 0) {
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFinish() {
        }
    };

    // ========================================================================
    // Constructor
    // ========================================================================

    @Inject
    public TzListActivityView (final TzListActivity activity) {
        super(activity);
        mActivity = activity;
    }

    // ========================================================================
    // User input
    // ========================================================================

    @OnClick(R.id.fab)
    public void onFabClick() {
        mActivity.mPresenter.onCreateTzClick();
    }

    @OnItemClick(android.R.id.list)
    public void onItemClick(final int pos) {
        mActivity.mPresenter.onItemClick(pos);
    }

    // ========================================================================
    // TzListView implementation
    // ========================================================================

    @Override
    public void initView() {
        mActivity.setContentView(R.layout.activity_tz_list);
        ButterKnife.bind(this, mActivity);
        mTzListView.setEmptyView(mActivity.findViewById(android.R.id.empty));
        mActivity.registerForContextMenu(mTzListView);
    }

    @Override
    public void showProgress(final boolean show) {
        super.showProgress(show, mProgressView, mContentView);
    }

    @Override
    public void startTimer() {
        mTimer.start();
    }

    @Override
    public void stopTimer() {
        mTimer.cancel();
    }

    @Override
    public void render(final List<Timezone> timezones, final boolean showAuthor) {
        mAdapter = new TzListViewAdapter(mActivity, timezones, showAuthor);
        mTzListView.setAdapter(mAdapter);
    }

    @Override
    public void showDeleteSuccess() {
        Snackbar.make(
                mTzListView,
                R.string.log_tz_delete_success,
                Snackbar.LENGTH_LONG
        ).show();
    }
}
