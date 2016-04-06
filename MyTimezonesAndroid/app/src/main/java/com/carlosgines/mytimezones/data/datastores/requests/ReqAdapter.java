package com.carlosgines.mytimezones.data.datastores.requests;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.ServerError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.RequestFuture;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class ReqAdapter {

    // ========================================================================
    // Constants
    // ========================================================================

    /**
     * Error code for expected error of no connection
     */
    public static final int NO_CONNECT_ERROR_CODE = -1;

    // ========================================================================
    // Public methods
    // ========================================================================

    /**
     * Send a synchronous volley request.
     */
    public static JSONObject sendReq(final Context ctx, final Req req)
            throws ExecutionException {
        final String route = req.getRoute();
        try {
            final JSONObject response = prepareReq(
                    ctx, req, route
            ).get(30, TimeUnit.SECONDS);
            Log.d(req.getRoute(), "Response:\n" + response.toString(4));
            return response;
        } catch (JSONException e) {
            throw new RuntimeException("JSONException at " + route, e);
        } catch (TimeoutException e) {
            throw new RuntimeException("TimeoutException at: " + route, e);
        } catch (InterruptedException e) {
            throw new RuntimeException("InterruptedException at " + route, e);
        }
    }

    // ========================================================================
    // Private methods
    // ========================================================================

    /**
     * Actually perform volley request sending
     */
    private static RequestFuture<JSONObject> prepareReq(final Context ctx,
            final Req req, final String route)
            throws JSONException {
        final Uri.Builder uriBuilder = new Uri.Builder()
                .encodedPath(VolleyAdapter.getBaseUrl())
                .appendEncodedPath(route);
        final RequestFuture<JSONObject> future = RequestFuture.newFuture();
        VolleyAdapter.getInstance(ctx).addToRequestQueue(
                new JsonObjectRequest(
                        req.getMethod(),
                        uriBuilder.toString(),
                        req.getJsonRequest(),
                        future,
                        future
                )
        );
        Log.d(route, "Sending request to: " + uriBuilder + "\n" + req.getJsonRequest());
        return future;
    }

    // ========================================================================
    // Helper methods
    // ========================================================================

    public static int getStatusCode(final ServerError e) {
        final int code;
        if (e.networkResponse != null) {
            code = e.networkResponse.statusCode;
        } else {
            code = 0;
        }
        return code;
    }

    public static void handleExecutionException(ExecutionException e,
            String route) {
        final Throwable cause = e.getCause();
        if (cause instanceof NoConnectionError) {
            throw new RuntimeException("NoConnectionError at " + route, cause);
        } else if (cause instanceof ServerError) {
            throw new RuntimeException("ServerError at " + route, cause);
        } else if (cause instanceof ParseError) {
            throw new RuntimeException("Volley ParseError at " + route, cause);
        } else {
            throw new RuntimeException("Unknown ExecutionException at " +
                    route, e);
        }
    }

    private static String getBody(ServerError e) {
        final String message;
        if (e.networkResponse != null) {
            message = new String(e.networkResponse.data);
        } else {
            message = "empty body";
        }
        return message;
    }
}