package com.carlosgines.mytimezones.presentation.views;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.carlosgines.mytimezones.R;
import com.carlosgines.mytimezones.presentation.di.DaggerActivityComponent;
import com.carlosgines.mytimezones.presentation.presenters.SigninPresenter;
import com.carlosgines.mytimezones.presentation.presenters.TzListPresenter;
import com.carlosgines.mytimezones.presentation.presenters.TzListView;

import javax.inject.Inject;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class TzListActivity extends BaseActivity implements TzListView {

    // ========================================================================
    // Member variables
    // ========================================================================

    @Inject
    TzListPresenter mPresenter;

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
    }

    private void initInjector() {
        DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build().inject(this);
    }

    // ========================================================================
    // User input
    // ========================================================================

    @OnClick(R.id.fab)
    public void onFabClick(View v) {
        Snackbar.make(v, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }

    // ========================================================================
    // Options menu
    // ========================================================================

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tz_list, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (item.getItemId()) {
            case R.id.action_signout:
                mPresenter.onSignoutClick();
                return true;
        }
        return super.onOptionsItemSelected(item);
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
}
