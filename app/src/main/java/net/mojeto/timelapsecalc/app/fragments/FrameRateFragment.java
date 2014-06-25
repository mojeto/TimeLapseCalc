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

import static java.lang.Math.round;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FrameRateFragment.OnFrameRateChangeListener} interface
 * to handle interaction events.
 * Use the {@link FrameRateFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FrameRateFragment extends Fragment {
    private static final String ARG_VALUE = "value";
    private static final String ARG_RECOUNT = "recount";

    private Double mValue;
    private int mPosition = 0;

    private OnFrameRateChangeListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param value Parameter 1.
     * @return A new instance of fragment FrameRateFragment.
     */
    public static FrameRateFragment newInstance(Double value, ValueForChange recount) {
        FrameRateFragment fragment = new FrameRateFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_VALUE, value);
        args.putInt(ARG_RECOUNT, fragment.recountToSpinnerPosition(recount));
        fragment.setArguments(args);
        return fragment;
    }
    public FrameRateFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mValue = getArguments().getDouble(ARG_VALUE);
            mPosition = getArguments().getInt(ARG_RECOUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_frame_rate, container, false);
        EditText edit = ((EditText) view.findViewById(R.id.edit_frame_rate));
        edit.setText(String.format(getResources().getString(R.string.edit_frame_rate_value),
                round(mValue)));

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

        ((Spinner) view.findViewById(R.id.spinner)).setSelection(mPosition);
        return view;
    }

    public void onClickCancel(View view) {
        if (mListener != null) {
            mListener.onCancel();
        }
    }

    public void onClickOk(View view) {
        mValue = Double.valueOf(((EditText) getView().findViewById(R.id.edit_frame_rate))
                .getText().toString());

        mPosition = ((Spinner) getView().findViewById(R.id.spinner)).getSelectedItemPosition();

        if ( mListener != null ) {
            mListener.onFrameRateChange(mValue, spinnerPositionToRecount(mPosition));
        }
    }

    public int recountToSpinnerPosition(ValueForChange recount) {
        switch (recount) {
            case CAMERA_FRAME_DURATION:
            case CAMERA_FRAMES:
                return 0;
            case CAMERA_RECORD_DURATION:
                return 1;
            case VIDEO_DURATION:
            default:
                return 2;
        }
    }

    public ValueForChange spinnerPositionToRecount(int position) {
        switch (position) {
            case 0:
                return ValueForChange.CAMERA_FRAME_DURATION;
            case 1:
                return ValueForChange.CAMERA_RECORD_DURATION;
            case 2:
            default:
                return ValueForChange.VIDEO_DURATION;
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnFrameRateChangeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnFrameRateChangeListener");
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
    public interface OnFrameRateChangeListener {

        public void onCancel();
        public void onFrameRateChange(double value, ValueForChange type);
    }

}
