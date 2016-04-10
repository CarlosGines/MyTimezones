package com.carlosgines.mytimezones.data.datastores.requests;

import android.content.Context;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.carlosgines.mytimezones.domain.models.User;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class SigninReq extends Req {

    // ========================================================================
    // Member variables
    // ========================================================================

    private final String mUserName;
    private final String mPassword;

    // ========================================================================
    // Member variables
    // ========================================================================

    public SigninReq(final String userName, final String password) {
        mUserName = userName;
        mPassword = password;
    }

    // ========================================================================
    // Public methods
    // ========================================================================

    public User signin(final Context ctx) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JsonOrgModule());
            return mapper.convertValue(super.send(ctx), User.class);
        } catch (ExecutionException e) {
            final Throwable cause = e.getCause();
            if (cause instanceof AuthFailureError) {
                return null;
            } else {
                super.handleExecutionException(e);
                throw null;
            }
        }
    }

    // ========================================================================
    // Req implementation
    // ========================================================================

    @Override
    public String getRoute() {
        return Contract.ROUTE;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }

    @Override
    public JSONObject getJsonRequest() throws JSONException {
        return new JSONObject()
                .put(Contract.REQ_USERNAME, mUserName)
                .put(Contract.REQ_PASSWORD, mPassword);
    }

    // ========================================================================
    // Request contract
    // ========================================================================

    public static abstract class Contract {

        private static final String ROUTE = "signin";
        private static final String REQ_USERNAME = "username";
        private static final String REQ_PASSWORD = "password";
    }
}