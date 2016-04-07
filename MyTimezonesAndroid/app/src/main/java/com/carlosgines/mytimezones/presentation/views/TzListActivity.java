package com.carlosgines.mytimezones.presentation.views;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.carlosgines.mytimezones.R;
import com.carlosgines.mytimezones.domain.models.Timezone;
import com.carlosgines.mytimezones.presentation.Navigator;
import com.carlosgines.mytimezones.presentation.di.DaggerActivityComponent;
import com.carlosgines.mytimezones.presentation.presenters.TzListPresenter;
import com.carlosgines.mytimezones.presentation.presenters.TzListView;
import com.carlosgines.mytimezones.presentation.views.adapters.TzListViewAdapter;

import java.util.List;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnItemClick;

public class TzListActivity extends BaseActivity implements TzListView {

    // ========================================================================
    // Member variables
    // ========================================================================

    @Inject
    TzListPresenter mPresenter;

    @Bind(android.R.id.list)
    ListView mTzListView;

    /**
     * Adapter of the timezones list view.
     */
    private TzListViewAdapter mAdapter;

    /**
     * Timer to update timezones current time.
     */
    private final CountDownTimer mTimer = new CountDownTimer(100000000, 1000) {

        public void onTick(long millisUntilFinished) {
            if(mAdapter != null && mAdapter.getCount() > 0) {
                mAdapter.notifyDataSetChanged();
            }
        }

        @Override
        public void onFinish() {
        }
    };

    // ========================================================================
    // Activity lifecycle methods
    // ========================================================================

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tz_list);
        ButterKnife.bind(this);

        this.initViews();
        this.initInjector();
        mPresenter.onInit();
    }

    protected void initViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mTzListView.setEmptyView(findViewById(android.R.id.empty));
        super.registerForContextMenu(mTzListView);
    }

    private void initInjector() {
        DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build().inject(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mTimer.start();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mTimer.cancel();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        if(resultCode == RESULT_OK && requestCode == Navigator.DEFAULT_RC) {
            mPresenter.onBackFromTzEdit(
                    (Timezone)data.getSerializableExtra(Navigator.TZ_KEY)
            );
        }
    }

    // ========================================================================
    // User input
    // ========================================================================

    @OnClick(R.id.fab)
    public void onFabClick(View v) {
        mPresenter.onCreateTzClick();
    }

    @OnItemClick(android.R.id.list)
    public void onItemClick(int pos) {
        mPresenter.onItemClick(pos);
    }

    // ========================================================================
    // Options menu
    // ========================================================================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.tz_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_signout:
                mPresenter.onSignoutClick();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ==========================================================================
    // Context menu
    // ==========================================================================

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.tz_list_ctx_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_delete:
                mPresenter.onDeleteClick(info.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }

    // ========================================================================
    // TzListView implementation
    // ========================================================================

    @Override
    public void showProgress(boolean show) {
    }

    @Override
    public void showNoConnection(boolean show) {
    }

    @Override
    public void render(List<Timezone> timezones) {
        if(mAdapter == null) {
            mAdapter = new TzListViewAdapter(this, timezones);
            mTzListView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }
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
