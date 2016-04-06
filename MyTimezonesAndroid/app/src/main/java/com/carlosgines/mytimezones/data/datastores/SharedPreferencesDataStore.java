package com.carlosgines.mytimezones.data.datastores;

import android.content.Context;
import android.content.SharedPreferences;

import com.carlosgines.mytimezones.data.datastores.requests.RegisterReq;
import com.carlosgines.mytimezones.data.datastores.requests.SigninReq;

import javax.inject.Inject;

/**
 * A repository to manage User related data.
 */
public class SharedPreferencesDataStore {

    // ==========================================================================
    // Constants
    // ==========================================================================

    /**
     * Name for the default SharedPreferences file.
     */
    private static final String DEFAULT_PREFS = "default_prefs";

    /**
     * Auth token key
     */
    private static final String PREFS_TOKEN = "token";

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
    public SharedPreferencesDataStore(Context ctx) {
        mCtx = ctx;
    }

    // ==========================================================================
    // SharedPreferencesDataStore public methods
    // ==========================================================================

    public void registerToken(final String token) {
        mCtx.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
                .edit()
                .putString(PREFS_TOKEN, token)
                .apply();
    }
}
