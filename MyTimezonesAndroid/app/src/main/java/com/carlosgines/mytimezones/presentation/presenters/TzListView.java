package com.carlosgines.mytimezones.presentation.presenters;

import com.carlosgines.mytimezones.domain.models.Timezone;

import java.util.List;

/**
 * View for Timezone List screen.
 */
public interface TzListView extends BaseView {

    /**
     * Render a list of timezones.
     */
    void render(List<Timezone> timezones);
}
