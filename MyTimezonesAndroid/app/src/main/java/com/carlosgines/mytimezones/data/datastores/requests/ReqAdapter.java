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

    // ==========================================================================
    // Constants
    // ==========================================================================

    // Generic response JSON keys
    public static final String RES_SUCCESS = "success";
    public static final String RES_ERROR_CODE = "error_code";
    public static final String RES_ERROR_MESSAGE = "error_message";

    /**
     * Error code for expected error of no connection
     */
    public static final int NO_CONNECT_ERROR_CODE = -1;

    // ==========================================================================
    // Public methods
    // ==========================================================================

    /**
     * Send a volley request in foreground mode. Not in use yet, synchrony stuff.
     */
    public static JSONObject sendFgReq(final Context ctx, final FgReq fgReq) {
        final String wsFuncName = fgReq.getWsFuncName();

        try {
            // Get specific json request parameters
            final JSONObject jsonRequest = fgReq.getJsonRequest();
            // Create the future request
            final RequestFuture<JSONObject> future = prepareReq(ctx, jsonRequest, wsFuncName);
            // Launch request
            final JSONObject response = future.get(30, TimeUnit.SECONDS);
            // Process the response
            return onFgReqResponse(ctx, fgReq, response);

        } catch (JSONException e) {
            // At getJsonRequest. Expected error during development. Print debug info.
            throw new RuntimeException("JSON exception at " + wsFuncName, e);
        } catch (TimeoutException e) {
            // Request timed out
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException("InterruptedException in " + wsFuncName + " RequestFuture", e);
        } catch (ExecutionException e) {
            // No connection or server error
            final Throwable cause = e.getCause();

            if (cause instanceof NoConnectionError) {
                throw new RuntimeException(cause);
            } else if (cause instanceof ServerError) {
                // Something went wrong in server.
                final String code = getStatusCode((ServerError) cause);
                final String message = "ERROR " + code + " on " + wsFuncName + ": " +
                        e.getClass().getSimpleName() + " - " + cause.getMessage();
                Log.e(wsFuncName, message + getBody((ServerError) cause));
                throw new RuntimeException(
                        message, new ServerResponseException(code, message)
                );
            } else if (cause instanceof ParseError) {
                throw new RuntimeException("Volley parsing exception", e);
            } else {
                throw new RuntimeException("Unknown ExecutionException", e);
            }
        }
    }

    // ==========================================================================
    // Private methods
    // ==========================================================================

    /**
     * Actually perform volley request sending
     */
    private static RequestFuture<JSONObject> prepareReq(
            Context ctx, JSONObject jsonRequest, String wsFuncName) throws JSONException {
        // Build the URL
        Uri.Builder uriBuilder = new Uri.Builder();
        uriBuilder.encodedPath(VolleyAdapter.getBaseUrl());
        uriBuilder.appendEncodedPath(wsFuncName);

        // Create the future request
        RequestFuture<JSONObject> future = RequestFuture.newFuture();
        JsonObjectRequest volleyReq = new JsonObjectRequest(
                Request.Method.POST, uriBuilder.toString(), jsonRequest, future, future);

        // Launch the request
        VolleyAdapter volley = VolleyAdapter.getInstance(ctx);
        volley.addToRequestQueue(volleyReq);

        Log.d(wsFuncName, "Sending request to: " + uriBuilder + "\n" + jsonRequest);

        return future;
    }

    private static JSONObject onFgReqResponse(Context ctx, FgReq fgReq, JSONObject response)
            throws JSONException {
        // Debug log
        String wsFuncName = fgReq.getWsFuncName();
        Log.d(wsFuncName, "Request responded with:\n" + response.toString(4));
        // Check if it is a successful response
        if (response.getBoolean(RES_SUCCESS)) {
            return response;
        } else {
            int errorCode = response.getInt(RES_ERROR_CODE);
            if (fgReq.isExpectedError(errorCode)) {
                // Expected error, handle it.
                fgReq.handleExpectedError(ctx, errorCode);
                throw new RuntimeException("Expected Error");
            } else {
                // Unexpected error.
                String code = "D" + errorCode;
                String message = "ERROR " + code + " on " + wsFuncName + ": " +
                        response.getString(RES_ERROR_MESSAGE);
                throw new RuntimeException(
                        message, new ServerResponseException(code, message)
                );
            }
        }
    }

    // ==========================================================================
    // Helper methods
    // ==========================================================================

    private static String getStatusCode(final ServerError e) {
        final String code;
        if (e.networkResponse != null) {
            code = "H" + e.networkResponse.statusCode;
        } else {
            code = "H000";
        }
        return code;
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