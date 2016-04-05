package com.carlosgines.mytimezones.data.datastores.requests;

import android.content.Context;

import org.json.JSONException;
import org.json.JSONObject;

public class SigninReq implements FgReq {

    // ==========================================================================
    // Member variables
    // ==========================================================================

    private String mUserName;
    private String mPassword;

    // ==========================================================================
    // Public methods
    // ==========================================================================

    public String signin(Context ctx, String username, String password) {
        mUserName = username;
        mPassword = password;
        JSONObject response = ReqAdapter.sendFgReq(ctx, this);
        try {
            return response.getString(Contract.RES_TOKEN);
        } catch (JSONException e) {
            throw new RuntimeException("JSON exception at " + getWsFuncName(), e);
        }
    }

    // ==========================================================================
    // FgReq implementation
    // ==========================================================================

    @Override
    public String getWsFuncName() {
        return Contract.WS;
    }

    @Override
    public JSONObject getJsonRequest() throws JSONException {
        // Build the JSON object to post
        final JSONObject jsonRequest = new JSONObject();
        jsonRequest.put(Contract.REQ_USERNAME, mUserName);
        jsonRequest.put(Contract.REQ_PASSWORD, mPassword);
        return jsonRequest;
    }

    @Override
    public boolean isExpectedError(int errorCode) {
        return false;
    }

    @Override
    public void handleExpectedError(Context ctx, int errorCode) {
    }

    // ==========================================================================
    // Request contract
    // ==========================================================================

    /**
     * Request contract
     */
    public static abstract class Contract {

        // Url suffix for the webservice call
        private static final String WS = "signin";

        // Request input params
        private static final String REQ_USERNAME = "username";
        private static final String REQ_PASSWORD = "password";

        // Response output params
        private static final String RES_TOKEN = "token";
    }
}