package net.mojeto.timelapsecalc.app.fragments;

import net.mojeto.timelapsecalc.app.Duration;
import net.mojeto.timelapsecalc.app.R;
import net.mojeto.timelapsecalc.app.ValueForChange;

/**
 * Created by honza on 27.6.14.
 */
public class VideoDurationFragment extends TimeSetFragment {

    public VideoDurationFragment() {
        //fragment empty constructor
    }

    public static VideoDurationFragment newInstance(Duration time, ValueForChange recount) {
        VideoDurationFragment fragment = new VideoDurationFragment();
        TimeSetFragment.setArguments(fragment, time, recount);
        return fragment;
    }

    @Override
    public int recountToSpinnerPosition(ValueForChange recount) {
        switch (recount) {
            case CAMERA_FRAME_DURATION:
                return 0;
            case CAMERA_RECORD_DURATION:
            default:
                return 1;
            case VIDEO_FRAME_RATE:
                return 2;
        }
    }

    @Override
    public ValueForChange spinnerPositionToRecount(int position) {
        switch (position) {
            case 0:
                return ValueForChange.CAMERA_FRAME_DURATION;
            case 1:
            default:
                return ValueForChange.CAMERA_RECORD_DURATION;
            case 2:
                return ValueForChange.VIDEO_FRAME_RATE;
        }
    }

    @Override
    public String[] getSpinnerItems() {
        return getResources().getStringArray(R.array.video_duration_names);
    }

    @Override
    public CharSequence getEditText() {
        return getResources().getText(R.string.edit_video_duration);
    }

    @Override
    public ValueForChange getValueType() {
        return ValueForChange.VIDEO_DURATION;
    }

    @Override
    public boolean isShowDays() {
        return true;
    }
}
