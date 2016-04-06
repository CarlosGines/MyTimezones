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

/**
 * Request sent by MyTimezones app.
 */
public abstract class Req {

    // ========================================================================
    // Non-abstract methods
    // ========================================================================

    /**
     * Send a synchronous volley request.
     * @param ctx A context.
     * @return The body of the response as JSON.
     */
    protected JSONObject send(final Context ctx)
            throws ExecutionException {
        final String route = getRoute();
        try {
            final JSONObject response = prepare(ctx)
                    .get(30, TimeUnit.SECONDS);
            Log.d(route, "Response:\n" + response.toString(4));
            return response;
        } catch (JSONException e) {
            throw new RuntimeException("JSONException at " + route, e);
        } catch (TimeoutException e) {
            throw new RuntimeException("TimeoutException at: " + route, e);
        } catch (InterruptedException e) {
            throw new RuntimeException("InterruptedException at " + route, e);
        }
    }

    /**
     * Prepare a volley request to be sent.
     * @param ctx A context.
     * @return A RequestFuture ready to be sent.
     */
    private RequestFuture<JSONObject> prepare(final Context ctx)
            throws JSONException {
        final Uri.Builder uriBuilder = new Uri.Builder()
                .encodedPath(VolleyAdapter.getBaseUrl())
                .appendEncodedPath(getRoute());
        final RequestFuture<JSONObject> future = RequestFuture.newFuture();
        VolleyAdapter.getInstance(ctx).addToRequestQueue(
                new JsonObjectRequest(
                        getMethod(),
                        uriBuilder.toString(),
                        getJsonRequest(),
                        future,
                        future
                )
        );
        Log.d(
                getRoute(),
                String.format(
                        "Sending %s request to %s\n%s",
                        getMethodName(),
                        uriBuilder,
                        getJsonRequest()
                )
        );
        return future;
    }

    /**
     * Transform the request method code to its name.
     * @return Name of the request.
     */
    private String getMethodName() {
        switch(getMethod()) {
            case Request.Method.GET:
                return "GET";
            case Request.Method.POST:
                return "POST";
            case Request.Method.PUT:
                return "PUT";
            case Request.Method.DELETE:
                return "DELETE";
            default:
                return "UNKNOWN";
        }
    }

    /**
     * Get the status code of an error response from server.
     * @param e error response of the server from Volley.
     * @return the status code of the response or 0.
     */
    protected int getStatusCode(final ServerError e) {
        final int code;
        if (e.networkResponse != null) {
            code = e.networkResponse.statusCode;
        } else {
            code = 0;
        }
        return code;
    }

    /**
     * Get the body of an error response from server.
     * @param e error response of the server from Volley.
     * @return the body of the response or 0.
     */
    private String getBody(ServerError e) {
        final String message;
        if (e.networkResponse != null) {
            message = new String(e.networkResponse.data);
        } else {
            message = "empty body";
        }
        return message;
    }

    /**
     * Always throws a RuntimeException that encapsulates either the cause of
     * the ExecutionException if known or the ExecutionException itself.
     * @param e The ExecutionException to be encapsulated.
     */
    protected void handleExecutionException(ExecutionException e) {
        final String route = getRoute();
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

    // ========================================================================
    // Abstract methods
    // ========================================================================

    /**
     * @return the route used to call Rest API.
     */
    protected abstract String getRoute();

    /**
     * Get the method used to call Rest API. Use the values from
     * {@link Request.Method}.
     *
     * @return the method used to call Rest API.
     */
    protected abstract int getMethod();

    /**
     * Build the JSONObject for the request from the foreground activity.
     *
     * @return the JSONObject with necessary params included.
     * @throws JSONException if a param name is null.
     */
    protected abstract JSONObject getJsonRequest() throws JSONException;
}
