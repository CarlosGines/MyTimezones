package com.carlosgines.mytimezones.presentation.presenters;

import com.carlosgines.mytimezones.domain.models.Timezone;

import java.util.List;

/**
 * View for Timezone List screen.
 */
public interface TzListView extends BaseView {

    /**
     * Start running timer to update current time on time zone records display.
     */
    void startTimer();

    /**
     * Stop running timer to update current time on time zone records display.
     */
    void stopTimer();

    /**
     * Render a list of timezones.
     * @param timezones the timezones to render.
     * @param showAuthor whether to show the author of the time zone records.
     */
    void render(List<Timezone> timezones, boolean showAuthor);

    /**
     * Show feedback about a time zone record successfully deleted.
     */
    void showDeleteSuccess();
}
