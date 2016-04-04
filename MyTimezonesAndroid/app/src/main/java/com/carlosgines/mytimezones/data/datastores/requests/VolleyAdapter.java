package com.carlosgines.mytimezones.data.datastores.requests;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * Helper class to send Volley requests
 */
public class VolleyAdapter {

    // ==========================================================================
    // Constants
    // ==========================================================================

    /**
     * Base URL of development server used for every web service call. Don't use
     * it directly, use {@code VolleyAdapter.getBaseUrl(Context)} to obtain the
     * right url dynamically.
     */
    // public final static String DEV_BASE_URL = "https://diggerapp.com/dev/ws/public/index.php";
    public final static String BASE_URL = "http://192.168.1.21:8080";

    // ==========================================================================
    // Member variables
    // ==========================================================================

    private static VolleyAdapter mInstance;
    private static Context mCtx;
    private RequestQueue mRequestQueue;

    // ==========================================================================
    // Getters & Setters
    // ==========================================================================

    /**
     * Get the base URL of the server to send requests. It can be changed by app
     * admins at runtime.
     */
    public static String getBaseUrl() {
        return BASE_URL;
    }

    /**
     * Set the base URL of the server to send requests (DEV, PRE or PRO)
     */
    public static void setBaseUrl(Context ctx, String baseUrl) {
        throw new UnsupportedOperationException("Base URL cannot be set");
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(mCtx.getApplicationContext());
        }
        return mRequestQueue;
    }

    // ==========================================================================
    // Instantiation
    // ==========================================================================

    private VolleyAdapter(Context context) {
        mCtx = context;
        mRequestQueue = getRequestQueue();
    }

    public static synchronized VolleyAdapter getInstance(Context context) {
        if (mInstance == null) {
            mInstance = new VolleyAdapter(context.getApplicationContext());
        }
        return mInstance;
    }

    // ==========================================================================
    // Public methods
    // ==========================================================================

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }
}