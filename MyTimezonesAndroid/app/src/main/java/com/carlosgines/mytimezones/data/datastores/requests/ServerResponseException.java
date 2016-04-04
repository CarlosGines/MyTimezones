package com.carlosgines.mytimezones.data.datastores.requests;

import android.util.Log;

import com.android.volley.ServerError;

/**
 * Exception for errors from server
 */
public class ServerResponseException extends RuntimeException {

    // ==========================================================================
    // Member variables
    // ==========================================================================

    private String mCode;

    // ==========================================================================
    // Constructor
    // ==========================================================================

    public ServerResponseException(String code, String detailMessage) {
        super(detailMessage);
        mCode = code;
    }

    // ==========================================================================
    // Getters & Setters
    // ==========================================================================

    /** @return the error code of the exception. It is a a letter and 3 digits.
     * D -> error return by server on purpose.
     * H -> HTML error
     * 3 digits -> error code, either HTML or from server logic
     */
    public String getErrorCode() {
        return mCode;
    }
}