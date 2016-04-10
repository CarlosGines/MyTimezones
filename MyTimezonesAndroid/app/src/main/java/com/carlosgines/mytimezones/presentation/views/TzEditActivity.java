package com.carlosgines.mytimezones.presentation.views;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.carlosgines.mytimezones.R;
import com.carlosgines.mytimezones.domain.models.Timezone;
import com.carlosgines.mytimezones.domain.models.User;
import com.carlosgines.mytimezones.presentation.di.DaggerActivityComponent;
import com.carlosgines.mytimezones.presentation.presenters.TzEditPresenter;
import com.carlosgines.mytimezones.presentation.presenters.TzEditView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Time zone edit Activity serving as TzEditView implementation.
 */
public class TzEditActivity extends BaseActivity implements TzEditView {

    // ========================================================================
    // Member variables
    // ========================================================================

    @Inject
    TzEditPresenter mPresenter;

    // UI references.
    @Bind(R.id.progress)
    View mProgressView;
    @Bind(R.id.main_content)
    View mContentView;
    @Bind(R.id.name)
    EditText mNameView;
    @Bind(R.id.city)
    EditText mCityView;
    @Bind(R.id.timeDiff)
    EditText mTimeDiffView;
    @Bind(R.id.author)
    TextView mAuthor;
    @Bind(R.id.action_button)
    Button mActionButton;

    // ========================================================================
    // Activity lifecycle methods
    // ========================================================================

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tz_edit);
        ButterKnife.bind(this);

        this.initViews();
        this.initInjector();
        mPresenter.onInit(getIntent().getExtras());
    }

    public void initViews() {
        mTimeDiffView.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView textView, int id,
                                                  KeyEvent keyEvent) {
                        if (id == EditorInfo.IME_ACTION_DONE) {
                            TzEditActivity.this.onActionClick();
                            return true;
                        }
                        return false;
                    }
                });
    }

    private void initInjector() {
        DaggerActivityComponent.builder()
                .applicationComponent(getApplicationComponent())
                .activityModule(getActivityModule())
                .build().inject(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    // ========================================================================
    // User input
    // ========================================================================

    @OnClick(R.id.action_button)
    public void onActionClick() {
        mPresenter.onActionClick(
                mNameView.getText().toString().trim(),
                mCityView.getText().toString().trim(),
                mTimeDiffView.getText().toString().trim()
        );
    }

    @Override
    public void onBackPressed() {
        mPresenter.onBackPressed();
    }

    // ========================================================================
    // TzEditView implementation
    // ========================================================================

    @Override
    public void showProgress(final boolean show) {
        super.closeKeyboard();
        super.showProgress(show, mProgressView, mContentView);
    }

    @Override
    public void setViewMode(final ViewMode mode, final Timezone tz,
                            final User user) {
        if (mode.equals(ViewMode.CREATE)) {
            setTitle(R.string.title_create_tz);
            mActionButton.setText(R.string.action_create_tz);
        } else {
            setTitle(tz.getName());
            mActionButton.setText(R.string.action_edit_tz);
            mNameView.setText(tz.getName());
            mCityView.setText(tz.getCity());
            mTimeDiffView.setText(String.valueOf(tz.getTimeDiff()));
            if(user.isAdmin()) {
                mAuthor.setVisibility(View.VISIBLE);
                mAuthor.setText(tz.getAuthor().getUsername());
            }
        }
    }

    @Override
    public void resetErrors() {
        mNameView.setError(null);
        mCityView.setError(null);
        mTimeDiffView.setError(null);
    }

    @Override
    public void setEmptyNameError() {
        mNameView.setError(getString(R.string.error_field_required));
        mNameView.requestFocus();
    }

    @Override
    public void setEmptyCityError() {
        mCityView.setError(getString(R.string.error_field_required));
        mCityView.requestFocus();
    }

    @Override
    public void setEmptyTimeDiffError() {
        mTimeDiffView.setError(getString(R.string.error_field_required));
        mTimeDiffView.requestFocus();
    }

    @Override
    public void setInvalidTimeDiffError() {
        mTimeDiffView.setError(getString(R.string.error_invalid_time_diff));
        mTimeDiffView.requestFocus();
    }

    @Override
    public void showEditSuccess(final ViewMode mode) {
        Snackbar.make(
                mActionButton,
                mode.equals(ViewMode.CREATE) ?
                        R.string.log_tz_creation_success :
                        R.string.log_tz_edit_success,
                Snackbar.LENGTH_LONG
        ).show();
    }
}
