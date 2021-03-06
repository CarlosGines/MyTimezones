package com.carlosgines.mytimezones.data.repositories;

import com.carlosgines.mytimezones.data.datastores.SharedPreferencesDataStore;
import com.carlosgines.mytimezones.data.datastores.TzRestDataStore;
import com.carlosgines.mytimezones.data.datastores.UserRestDataStore;
import com.carlosgines.mytimezones.domain.models.Timezone;
import com.carlosgines.mytimezones.domain.repositories.TzRepository;
import com.carlosgines.mytimezones.domain.repositories.UserRepository;

import java.util.List;

import javax.inject.Inject;

/**
 * A UserRepository for managing timezones data.
 */
public class TzDataRepository implements TzRepository {

    private final TzRestDataStore mTzRestDataStore;

    @Inject
    public TzDataRepository(final TzRestDataStore tzRestDataStore) {
        mTzRestDataStore = tzRestDataStore;
    }

    @Override
    public Timezone create(final Timezone tz) {
        return mTzRestDataStore.create(tz);
    }

    @Override
    public List<Timezone> get() {
        return mTzRestDataStore.get();
    }

    @Override
    public Timezone edit(final Timezone tz) {
        return mTzRestDataStore.edit(tz);
    }

    @Override
    public List<Timezone> search(final String text) {
        return mTzRestDataStore.search(text);
    }

    @Override
    public Timezone delete(final Timezone tz) {
        return mTzRestDataStore.delete(tz);
    }
}
