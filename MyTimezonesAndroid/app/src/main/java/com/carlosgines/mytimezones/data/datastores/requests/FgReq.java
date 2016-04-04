package com.carlosgines.mytimezones.data.datastores.requests;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public interface FgReq {

    /**
     * @return the webservice function name
     */
    String getWsFuncName();

    /**
     * Build the JSONObject for the request from the foreground activity
     *
     * @return the JSONObject with necessary params included
     * @throws JSONException if a param name is null
     */
    JSONObject getJsonRequest() throws JSONException;

    /**
     * Check whether a certain error is expected for this request
     *
     * @param errorCode error received
     * @return whether it is expected
     */
    boolean isExpectedError(int errorCode);

    /**
     * Handle an unsuccessful but expected response from server
     *
     * @param ctx       the context
     * @param errorCode error received
     */
    void handleExpectedError(Context ctx, int errorCode);
}
