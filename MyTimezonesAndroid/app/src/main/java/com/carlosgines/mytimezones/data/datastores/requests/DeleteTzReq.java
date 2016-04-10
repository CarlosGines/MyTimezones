package com.carlosgines.mytimezones.data.datastores.requests;

import android.content.Context;

import com.android.volley.Request;
import com.carlosgines.mytimezones.domain.models.Timezone;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

public class DeleteTzReq extends Req {

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

    public DeleteTzReq(final Timezone tz) {
        mTz = tz;
    }

    // ========================================================================
    // Public methods
    // ========================================================================

    public Timezone deleteTz(final Context ctx, final String token) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JsonOrgModule());
            return mapper.convertValue(super.send(ctx, token), Timezone.class);
        } catch (ExecutionException e) {
            super.handleExecutionException(e);
            throw null;
        }
    }

    // ========================================================================
    // Req implementation
    // ========================================================================

    @Override
    public String getRoute() {
        return String.format("%s/%s", ROUTE, mTz.get_id());
    }

    @Override
    public int getMethod() {
        return Request.Method.DELETE;
    }

    @Override
    public JSONObject getJsonRequest() throws JSONException {
        return null;
    }
}
