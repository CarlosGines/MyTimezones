package com.carlosgines.mytimezones.data.datastores.requests;

import android.content.Context;

import com.android.volley.Request;
import com.carlosgines.mytimezones.domain.models.Timezone;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsonorg.JsonOrgModule;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class GetTzListReq extends Req {

    // ========================================================================
    // Public methods
    // ========================================================================

    public List<Timezone> getTzList(final Context ctx, final String token) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JsonOrgModule());
            return mapper.convertValue(
                    super.send(ctx, token).getJSONArray(Contract.RES_TZS),
                    new TypeReference<List<Timezone>>(){}
            );
        } catch (ExecutionException e) {
            handleExecutionException(e);
            throw null;
        } catch (JSONException e) {
            throw new RuntimeException("JSON exception at " + getRoute(), e);
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
        return Request.Method.GET;
    }

    @Override
    public JSONObject getJsonRequest() throws JSONException {
        return null;
    }

    // ========================================================================
    // Request contract
    // ========================================================================

    public static abstract class Contract {

        private static final String ROUTE = "timezones";
        private static final String RES_TZS = "tzs";
    }
}
