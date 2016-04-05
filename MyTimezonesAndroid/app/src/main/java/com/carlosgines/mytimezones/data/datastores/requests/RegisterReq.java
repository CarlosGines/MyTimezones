package com.carlosgines.mytimezones.data.datastores.requests;

import android.content.Context;

import com.android.volley.ServerError;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class RegisterReq implements Req {

    // ========================================================================
    // Member variables
    // ========================================================================

    private final String mUserName;
    private final String mPassword;

    // ========================================================================
    // Member variables
    // ========================================================================

    public RegisterReq(final String userName, final String password) {
        mUserName = userName;
        mPassword = password;
    }

    // ========================================================================
    // Public methods
    // ========================================================================

    public String register(final Context ctx) {
        try {
            return ReqAdapter.sendFgReq(ctx, this)
                    .getString(Contract.RES_TOKEN);
        } catch (ExecutionException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof ServerError &&
                    ReqAdapter.getStatusCode((ServerError) cause) == 409) {
                return "";
            } else {
                ReqAdapter.handleExecutionException(e, getRoute());
                throw null;
            }
        } catch (JSONException e) {
            throw new RuntimeException("JSON exception at " + getRoute(), e);
        }
    }

    // ========================================================================
    // FgReq implementation
    // ========================================================================

    @Override
    public String getRoute() {
        return Contract.ROUTE;
    }

    @Override
    public JSONObject getJsonRequest() throws JSONException {
        // Build the JSON object to post
        return new JSONObject()
                .put(Contract.REQ_USERNAME, mUserName)
                .put(Contract.REQ_PASSWORD, mPassword);
    }

    @Override
    public boolean isExpectedError(int statusCode) {
        return false;
    }

    @Override
    public void handleExpectedError(Context ctx, int errorCode) {
    }

    // ========================================================================
    // Request contract
    // ========================================================================

    /**
     * Request contract
     */
    public static abstract class Contract {

        // Url suffix for the webservice call
        private static final String ROUTE = "register";

        // Request input params
        private static final String REQ_USERNAME = "username";
        private static final String REQ_PASSWORD = "password";

        // Response output params
        private static final String RES_TOKEN = "token";
    }
}