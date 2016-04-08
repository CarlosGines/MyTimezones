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
     * Get the list of timezone records that this user has access to.
     * @return a list of timezone records.
     */
    List<Timezone> get();

    /**
     * Edit a timezone record.
     * @return the edited record.
     */
    Timezone edit(Timezone tz);

    /**
     * Get the list of timezone records that this user has access to and meet
     * the filtering criteria of time zones searched by name text.
     * @param text A text to filter the time zones list.
     * @return a list of timezone records.
     */
    List<Timezone> search(String text);

    /**
     * Delete a timezone record.
     * @return the deleted record.
     */
    Timezone delete(Timezone tz);
}
