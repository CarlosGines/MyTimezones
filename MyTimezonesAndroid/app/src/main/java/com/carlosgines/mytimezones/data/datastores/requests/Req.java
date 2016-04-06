package com.carlosgines.mytimezones.data.datastores.requests;

import android.content.Context;

import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Request sent by MyTimezones app.
 */
public interface Req {

    /**
     * @return the route used to call Rest API.
     */
    String getRoute();

    /**
     * Get the method used to call Rest API. Use the values from
     * {@link Request.Method}.
     *
     * @return the method used to call Rest API.
     */
    int getMethod();

    /**
     * Build the JSONObject for the request from the foreground activity.
     *
     * @return the JSONObject with necessary params included.
     * @throws JSONException if a param name is null.
     */
    JSONObject getJsonRequest() throws JSONException;

    /**
     * Check whether a certain error is expected for this request.
     *
     * @param errorCode error received.
     * @return whether it is expected.
     */
    boolean isExpectedError(int errorCode);

    /**
     * Handle an unsuccessful but expected response from server.
     *
     * @param ctx       the context.
     * @param errorCode error received.
     */
    void handleExpectedError(Context ctx, int errorCode);
}
