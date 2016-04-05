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
    private final Context mCtx;

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

    public String signin(final String userName, final String password) {
        return new SigninReq(userName, password).signin(mCtx);
    }
}
