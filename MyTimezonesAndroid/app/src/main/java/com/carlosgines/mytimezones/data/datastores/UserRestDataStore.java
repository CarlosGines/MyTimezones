package com.carlosgines.mytimezones.data.datastores;

import android.content.Context;

import com.carlosgines.mytimezones.data.datastores.requests.SigninReq;

import javax.inject.Inject;

/**
 * Created by efrel on 4/4/16.
 */
public class UserRestDataStore {
    // ==========================================================================
    // Member variables
    // ==========================================================================

    /**
     * A context
     */
    private Context mCtx;

    // ==========================================================================
    // Constructor
    // ==========================================================================

    @Inject
    public UserRestDataStore(Context ctx) {
        mCtx = ctx;
    }

    // ==========================================================================
    // DecDataStore implementation
    // ==========================================================================

    public String signin(String email, String password) {
        SigninReq signinReq = new SigninReq();
        return signinReq.signin(mCtx, email, password);
    }
}
