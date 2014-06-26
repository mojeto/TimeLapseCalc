package net.mojeto.timelapsecalc.app.fragments;

import net.mojeto.timelapsecalc.app.Duration;
import net.mojeto.timelapsecalc.app.R;
import net.mojeto.timelapsecalc.app.ValueForChange;

/**
 * Created by honza on 26.6.14.
 */
public class CameraFrameDurationFragment extends TimeSetFragment {

    public CameraFrameDurationFragment() {

    }

    public static CameraFrameDurationFragment newInstance(Duration time, ValueForChange recount) {
        CameraFrameDurationFragment fragment = new CameraFrameDurationFragment();
        TimeSetFragment.setArguments(fragment, time, recount);
        return fragment;
    }

    @Override
    public int recountToSpinnerPosition(ValueForChange recount) {
        switch (recount) {
            case CAMERA_RECORD_DURATION:
            default:
                return 0;
            case VIDEO_FRAME_RATE:
                return 1;
            case VIDEO_DURATION:
                return 2;
        }
    }

    @Override
    public ValueForChange spinnerPositionToRecount(int position) {
        switch (position) {
            case 0:
                return ValueForChange.CAMERA_RECORD_DURATION;
            case 1:
            default:
                return ValueForChange.VIDEO_FRAME_RATE;
            case 2:
                return ValueForChange.VIDEO_DURATION;
        }
    }

    @Override
    public String[] getSpinnerItems() {
        return getResources().getStringArray(R.array.camera_frame_duration_names);
    }

    @Override
    public CharSequence getEditText() {
        return getResources().getText(R.string.edit_camera_frame_duration);
    }

    @Override
    public ValueForChange getValueType() {
        return ValueForChange.CAMERA_FRAME_DURATION;
    }
}
