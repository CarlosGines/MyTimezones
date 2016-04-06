package com.carlosgines.mytimezones.data.datastores;

import android.content.Context;

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

    public String getToken() {
        return mCtx.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
                .getString(PREFS_TOKEN, "");
    }

    public void deleteToken() {
        mCtx.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
                .edit()
                .remove(PREFS_TOKEN)
                .apply();
    }
}
