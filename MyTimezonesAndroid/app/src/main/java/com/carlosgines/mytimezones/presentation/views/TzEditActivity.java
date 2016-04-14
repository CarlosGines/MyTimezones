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
 * Time zone record edit Activity used in TzEditView implementation.
 */
public class TzEditActivity extends BaseActivity {

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
    protected void onDestroy() {
        super.onDestroy();
        mPresenter.onDestroy();
    }

    // ========================================================================
    // Back
    // ========================================================================

    @Override
    public void onBackPressed() {
        mPresenter.onBackPressed();
    }
}
