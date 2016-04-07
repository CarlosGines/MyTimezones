package com.carlosgines.mytimezones.data.datastores.requests;

import com.android.volley.AuthFailureError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Specific volley request used to perform our requests.
 */
public class TzVolleyReq extends JsonObjectRequest {

    private final String mToken;

    public TzVolleyReq(int method, String url, JSONObject jsonRequest,
                       RequestFuture<JSONObject> future, String token) {
        super(method, url, jsonRequest, future, future);
        mToken = token;
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        final Map<String, String> headers;
        if(mToken != null) {
            headers = new HashMap<>();
            headers.put("Authorization", "Bearer " + mToken);
        } else {
            headers = super.getHeaders();
        }
        return headers;
    }
}
