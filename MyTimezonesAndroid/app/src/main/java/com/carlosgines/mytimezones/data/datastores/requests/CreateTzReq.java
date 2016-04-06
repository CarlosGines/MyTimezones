package com.carlosgines.mytimezones.data.datastores.requests;

import android.content.Context;

import com.android.volley.Request;
import com.carlosgines.mytimezones.domain.models.Timezone;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class CreateTzReq extends Req {

    // ========================================================================
    // Constants
    // ========================================================================

    private static final String ROUTE = "timezones";

    // ========================================================================
    // Member variables
    // ========================================================================

    private final Timezone mTz;

    // ========================================================================
    // Constructor
    // ========================================================================

    public CreateTzReq(final Timezone tz) {
        mTz = tz;
    }

    // ========================================================================
    // Public methods
    // ========================================================================

    public void createTz(final Context ctx) {
        try {
            send(ctx);
        } catch (ExecutionException e) {
            handleExecutionException(e);
        }
    }

    // ========================================================================
    // Req implementation
    // ========================================================================

    @Override
    public String getRoute() {
        return ROUTE;
    }

    @Override
    public int getMethod() {
        return Request.Method.POST;
    }

    @Override
    public JSONObject getJsonRequest() throws JSONException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JsonOrgModule());
        return mapper.convertValue(mTz, JSONObject.class);
    }
}
