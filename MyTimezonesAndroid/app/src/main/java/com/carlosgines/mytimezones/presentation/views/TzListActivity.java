package com.carlosgines.mytimezones.presentation.views;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;

import com.carlosgines.mytimezones.R;
import com.carlosgines.mytimezones.domain.models.Timezone;
import com.carlosgines.mytimezones.presentation.Navigator;
import com.carlosgines.mytimezones.presentation.di.DaggerActivityComponent;
import com.carlosgines.mytimezones.presentation.presenters.TzListPresenter;

import javax.inject.Inject;

/**
 * Time zone records Activity used in TzListView implementation.
 */
public class TzListActivity extends BaseActivity {

    // ========================================================================
    // Member variables
    // ========================================================================

    @Inject
    TzListPresenter mPresenter;

    // ========================================================================
    // Activity lifecycle methods
    // ========================================================================

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initInjector();
        mPresenter.onCreate(getIntent().getExtras());
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
        mPresenter.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mPresenter.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    @Override
    protected void onActivityResult(final int requestCode, final int resultCode,
                                    final Intent data) {
        if (resultCode == RESULT_OK && requestCode == Navigator.DEFAULT_RC) {
            mPresenter.onBackFromTzEdit(
                    (Timezone) data.getSerializableExtra(Navigator.TZ_KEY)
            );
        }
    }

    // ========================================================================
    // Options menu
    // ========================================================================

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        getMenuInflater().inflate(R.menu.tz_list_menu, menu);
        (
                (SearchView) MenuItemCompat.getActionView(
                        menu.findItem(R.id.action_search)
                )
        ).setOnQueryTextListener(
                new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return true;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        mPresenter.onQueryTextChange(newText);
                        return true;
                    }
                }
        );
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_signout:
                mPresenter.onSignoutClick();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // ========================================================================
    // Context menu
    // ========================================================================

    @Override
    public void onCreateContextMenu(final ContextMenu menu, final View v,
                                    final ContextMenu
                                            .ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        getMenuInflater().inflate(R.menu.tz_list_ctx_menu, menu);
    }

    @Override
    public boolean onContextItemSelected(final MenuItem item) {
        AdapterView.AdapterContextMenuInfo info =
                (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        switch (item.getItemId()) {
            case R.id.menu_delete:
                mPresenter.onDeleteClick(info.position);
                return true;
        }
        return super.onContextItemSelected(item);
    }
}
