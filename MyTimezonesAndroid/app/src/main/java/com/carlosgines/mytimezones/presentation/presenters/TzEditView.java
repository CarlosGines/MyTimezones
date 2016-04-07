package com.carlosgines.mytimezones.presentation.presenters;

import com.carlosgines.mytimezones.domain.models.Timezone;

/**
 * View for Time zone create and edit screen.
 */
public interface TzEditView extends BaseView{

    /**
     * Enum to indicate view mode that can be create or
     * edit view
     */
    enum ViewMode {CREATE, EDIT};

    /**
     * Set view mode.
     * @param mode view mode.
     * @param tz The time zone record being edited if any.
     */
    void setViewMode(ViewMode mode, Timezone tz);

    /**
     * Delete all error messages on fields.
     */
    void resetErrors();

    /**
     * Set empty error message on name field.
     */
    void setEmptyNameError();

    /**
     * Set empty error message on city field.
     */
    void setEmptyCityError();

    /**
     * Set empty error message on time difference field.
     */
    void setEmptyTimeDiffError();

    /**
     * Set invalid error message on time diff field.
     */
    void setInvalidTimeDiffError();

    /**
     * Show feedback about a time zone record successfully created or edited.
     */
    void showEditSuccess(ViewMode mode);

}
