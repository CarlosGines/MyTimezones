package com.carlosgines.mytimezones.presentation.views;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.carlosgines.mytimezones.R;
import com.carlosgines.mytimezones.presentation.di.DaggerActivityComponent;
import com.carlosgines.mytimezones.presentation.presenters.SigninPresenter;
import com.carlosgines.mytimezones.presentation.presenters.SigninView;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Sign in Activity used in SigninView implementation.
 */
public class SigninActivity extends BaseActivity {

    // ========================================================================
    // Member variables
    // ========================================================================

    @Inject
    SigninPresenter mPresenter;

    // ========================================================================
    // Activity lifecycle methods
    // ========================================================================

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.initInjector();
        mPresenter.onCreate();
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
}

