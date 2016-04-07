package com.carlosgines.mytimezones.domain.repositories;

import com.carlosgines.mytimezones.domain.models.Timezone;

import java.util.List;

/**
 * Interface that represents a Repository to manage Timezone related data.
 */
public interface TzRepository {

    /**
     * Create a new timezone record for the current user.
     * @param tz the timezone to create.
     * @return The created time zone.
     */
    Timezone create(Timezone tz);

    /**
     * Get the list of timezone records of this user.
     * @return the list of timezone records of this user.
     */
    List<Timezone> get();
}
