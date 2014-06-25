package net.mojeto.timelapsecalc.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Spinner;

import net.mojeto.timelapsecalc.app.R;
import net.mojeto.timelapsecalc.app.ValueForChange;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SumOfFramesFragment.OnSumOfFramesChangeListener} interface
 * to handle interaction events.
 * Use the {@link SumOfFramesFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class SumOfFramesFragment extends Fragment {
    private static final String ARG_SUM = "sum";
    private static final String ARG_CAMERA_RECOUNT = "cameraPosition";
    private static final String ARG_VIDEO_RECOUNT = "videoPosition";

    private long mSum = 1;
    private int mCameraPosition = 0;
    private int mVideoPosition = 0;

    private OnSumOfFramesChangeListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param sumOfFrames long number of frames
     * @param cameraRecount what recount on camera
     * @param videoRecount what recount on video
     * @return A new instance of fragment SumOfFramesFragment.
     */
    public static SumOfFramesFragment newInstance(long sumOfFrames,
                                                  ValueForChange cameraRecount,
                                                  ValueForChange videoRecount) {
        SumOfFramesFragment fragment = new SumOfFramesFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_SUM, sumOfFrames);
        args.putInt(ARG_CAMERA_RECOUNT, fragment.recountCameraToSpinnerPosition(cameraRecount));
        args.putInt(ARG_VIDEO_RECOUNT, fragment.recountVideoToSpinnerPosition(videoRecount));
        fragment.setArguments(args);
        return fragment;
    }
    public SumOfFramesFragment() {
        // Required empty public constructor
    }

    public int recountCameraToSpinnerPosition(ValueForChange recount) {
        switch (recount) {
            case CAMERA_FRAME_DURATION:
                return 0;
            case CAMERA_RECORD_DURATION:
            default:
                return 1;
        }
    }

    public ValueForChange spinnerPositionToRecountCamera(int position) {
        switch (position) {
            case 0:
                return ValueForChange.CAMERA_FRAME_DURATION;
            case 1:
            default:
                return ValueForChange.CAMERA_RECORD_DURATION;
        }
    }

    public int recountVideoToSpinnerPosition(ValueForChange recount) {
        switch (recount) {
            case VIDEO_FRAME_RATE:
                return 0;
            case VIDEO_DURATION:
            default:
                return 1;
        }
    }

    public ValueForChange spinnerPositionToRecountVideo(int position) {
        switch (position) {
            case 0:
                return ValueForChange.VIDEO_FRAME_RATE;
            case 1:
            default:
                return ValueForChange.CAMERA_RECORD_DURATION;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putLong(ARG_SUM, mSum);
        outState.putInt(ARG_CAMERA_RECOUNT, mCameraPosition);
        outState.putInt(ARG_VIDEO_RECOUNT, mVideoPosition);

    }

    public void loadSaveInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mSum = savedInstanceState.getLong(ARG_SUM);
            mCameraPosition = savedInstanceState.getInt(ARG_CAMERA_RECOUNT);
            mVideoPosition = savedInstanceState.getInt(ARG_VIDEO_RECOUNT);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSaveInstanceState(savedInstanceState != null ? savedInstanceState : getArguments());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_sum_of_frames, container, false);

        EditText edit = ((EditText) view.findViewById(R.id.edit_sum));
        edit.setText(String.valueOf(mSum));

        view.findViewById(R.id.button_ok).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickOk(v);
            }
        });

        view.findViewById(R.id.button_cancel).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickCancel(v);
            }
        });

        ((Spinner) view.findViewById(R.id.spinner_camera)).setSelection(mCameraPosition);
        ((Spinner) view.findViewById(R.id.spinner_video)).setSelection(mVideoPosition);
        return view;
    }

    public void onClickCancel(View view) {
        if (mListener != null) {
            mListener.onCancel();
        }
    }

    public void onClickOk(View view) {
        mSum = Long.valueOf(((EditText) getView().findViewById(R.id.edit_sum)).getText()
                .toString());

        mCameraPosition = ((Spinner) getView().findViewById(R.id.spinner_camera))
                .getSelectedItemPosition();

        mVideoPosition = ((Spinner) getView().findViewById(R.id.spinner_video))
                .getSelectedItemPosition();

        if ( mListener != null ) {
            mListener.onSumOfFramesChange(mSum, spinnerPositionToRecountCamera(mCameraPosition),
                    spinnerPositionToRecountVideo(mVideoPosition));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnSumOfFramesChangeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSumOfFramesChangeListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnSumOfFramesChangeListener {

        public void onCancel();

        public void onSumOfFramesChange(long sumOfFrames,
                                        ValueForChange cameraRecount,
                                        ValueForChange videoRecount);
    }

}
