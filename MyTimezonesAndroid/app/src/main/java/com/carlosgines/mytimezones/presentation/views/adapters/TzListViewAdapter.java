package com.carlosgines.mytimezones.presentation.views.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.carlosgines.mytimezones.R;
import com.carlosgines.mytimezones.domain.models.Timezone;

import java.util.List;

/**
 * Custom ArrayAdapter of Timezones for the ListView on TzListActivity
 */
public class TzListViewAdapter extends ArrayAdapter<Timezone> {

    // ========================================================================
    // Member variables
    // ========================================================================

	/**
	 * Inflater to create the views from XML.
	 */
	private final LayoutInflater mInflater;

    /**
     * Whether the author of the record needs to be displayed.
     */
    private final boolean mShowAuthor;

    // ========================================================================
    // Constructor
    // ========================================================================

    public TzListViewAdapter(final Context ctx, final List<Timezone> tzList,
                             final boolean showAuthor) {
        super(ctx, 0, tzList);
		mInflater = LayoutInflater.from(ctx);
        mShowAuthor = showAuthor;
    }

    // ========================================================================
    // ArrayAdapter methods
    // ========================================================================

	@Override
	public View getView(final int position, final View convertView,
                        final ViewGroup parent) {
		final View resultView;
		if (convertView == null) {
			resultView = mInflater.inflate(R.layout.row_tz, parent, false);
		} else {
			resultView = convertView;
		}
		final Timezone tz = getItem(position);
		final TextView nameTv = (TextView) resultView.findViewById(R.id.name);
        final TextView cityTv = (TextView) resultView.findViewById(R.id.city);
        final TextView timeDiffTv = (TextView) resultView.
                findViewById(R.id.timeDiff);
        final TextView currentTimeTv = (TextView) resultView.
                findViewById(R.id.currentTime);
        final TextView authorTv = (TextView) resultView.
                findViewById(R.id.author);
        nameTv.setText(tz.getName());
        cityTv.setText(tz.getCity());
        if (tz.getTimeDiff() > 0) {
            timeDiffTv.setText(String.format("+%d", tz.getTimeDiff()));
        } else {
            timeDiffTv.setText(String.format("%d", tz.getTimeDiff()));
        }
        currentTimeTv.setText(tz.getFormattedCurrentDateTime());
        if (mShowAuthor) {
            authorTv.setVisibility(View.VISIBLE);
            authorTv.setText(tz.getAuthor().getUsername());
        }
        return resultView;
	}
}
