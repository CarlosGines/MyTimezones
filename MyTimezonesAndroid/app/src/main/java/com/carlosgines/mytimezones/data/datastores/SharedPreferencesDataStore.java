package com.carlosgines.mytimezones.data.datastores;

import android.content.Context;
import android.content.SharedPreferences;

import com.carlosgines.mytimezones.domain.models.User;

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

    /**
     * ID of the authenticated user.
     */
    private static final String PREFS_USER_ID = "user_id";

    /**
     * User name of the authenticated user.
     */
    private static final String PREFS_USERNAME = "username";

    /**
     * User name of the authenticated user.
     */
    private static final String PREFS_IS_ADMIN = "is_admin";

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

    public User getAuthUser() {
        final SharedPreferences sp = mCtx.getSharedPreferences(
                DEFAULT_PREFS, Context.MODE_PRIVATE
        );
        final String _id = sp.getString(PREFS_USER_ID, "");
        if(!_id.isEmpty()) {
            User user = new User();
            user.set_id(_id);
            user.setUsername(sp.getString(PREFS_USERNAME, ""));
            user.setAdmin(sp.getBoolean(PREFS_IS_ADMIN, false));
            user.setToken(sp.getString(PREFS_TOKEN, ""));
            return user;
        }
        return null;
    }

    public void setAuthUser(final User user) {
        mCtx.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
                .edit()
                .putString(PREFS_USER_ID, user.get_id())
                .putString(PREFS_USERNAME, user.getUsername())
                .putBoolean(PREFS_IS_ADMIN, user.isAdmin())
                .putString(PREFS_TOKEN, user.getToken())
                .apply();
    }

    public void deleteAuthUser() {
        mCtx.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
                .edit()
                .remove(PREFS_USER_ID)
                .remove(PREFS_USERNAME)
                .remove(PREFS_IS_ADMIN)
                .remove(PREFS_TOKEN)
                .apply();
    }

    public String getToken() {
        return mCtx.getSharedPreferences(DEFAULT_PREFS, Context.MODE_PRIVATE)
                .getString(PREFS_TOKEN, "");
    }
}
