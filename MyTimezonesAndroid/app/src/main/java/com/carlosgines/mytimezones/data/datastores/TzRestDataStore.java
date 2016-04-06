package com.carlosgines.mytimezones.data.datastores;

import android.content.Context;

import com.carlosgines.mytimezones.data.datastores.requests.CreateTzReq;
import com.carlosgines.mytimezones.data.datastores.requests.GetTzListReq;
import com.carlosgines.mytimezones.data.datastores.requests.RegisterReq;
import com.carlosgines.mytimezones.data.datastores.requests.SigninReq;
import com.carlosgines.mytimezones.domain.models.Timezone;

import java.util.List;

import javax.inject.Inject;

/**
 * A repository to manage Timezone related data.
 */
public class TzRestDataStore {
    // ==========================================================================
    // Member variables
    // ==========================================================================

    /**
     * A context
     */
    private final Context mCtx;

    // ==========================================================================
    // Constructor
    // ==========================================================================

    @Inject
    public TzRestDataStore(Context ctx) {
        mCtx = ctx;
    }

    // ==========================================================================
    // UserRestDataStore public methods
    // ==========================================================================

    public void create(final Timezone tz) {
        new CreateTzReq(tz).createTz(mCtx);
    }

    public List<Timezone> get() {
        return new GetTzListReq().getTzList(mCtx);
    }
}
