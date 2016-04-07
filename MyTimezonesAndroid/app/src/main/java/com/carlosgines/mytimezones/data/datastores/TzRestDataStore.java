package com.carlosgines.mytimezones.data.datastores;

import android.content.Context;

import com.carlosgines.mytimezones.data.datastores.requests.CreateTzReq;
import com.carlosgines.mytimezones.data.datastores.requests.GetTzListReq;
import com.carlosgines.mytimezones.data.datastores.requests.RegisterReq;
import com.carlosgines.mytimezones.data.datastores.requests.SigninReq;
import com.carlosgines.mytimezones.domain.models.Timezone;
import com.carlosgines.mytimezones.domain.repositories.UserRepository;

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
    private final UserRepository mUserRepository;

    // ==========================================================================
    // Constructor
    // ==========================================================================

    @Inject
    public TzRestDataStore(final Context ctx, final UserRepository userRepository) {
        mCtx = ctx;
        mUserRepository = userRepository;
    }

    // ==========================================================================
    // UserRestDataStore public methods
    // ==========================================================================

    public void create(final Timezone tz) {
        new CreateTzReq(tz).createTz(mCtx);
    }

    public List<Timezone> get() {
        return new GetTzListReq().getTzList(mCtx, mUserRepository.getToken());
    }
}
