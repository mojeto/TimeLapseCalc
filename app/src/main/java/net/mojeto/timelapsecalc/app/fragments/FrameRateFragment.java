package net.mojeto.timelapsecalc.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
 * {@link FrameRateFragment.OnFrameRateChangeListener} interface
 * to handle interaction events.
 * Use the {@link FrameRateFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class FrameRateFragment extends Fragment {
    private static final String ARG_VALUE = "value";

    private Double mValue;

    private OnFrameRateChangeListener mListener;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param value Parameter 1.
     * @return A new instance of fragment FrameRateFragment.
     */
    public static FrameRateFragment newInstance(Double value) {
        FrameRateFragment fragment = new FrameRateFragment();
        Bundle args = new Bundle();
        args.putDouble(ARG_VALUE, value);
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
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_frame_rate, container, false);
        ((EditText) view.findViewById(R.id.edit_frame_rate))
                .setText(String.format(getResources().getString(R.string.edit_frame_rate_value),
                        mValue));
        view.findViewById(R.id.button_ok).setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                onClickOk(v);
            }
        });
        return view;
    }

    public void onClickOk(View view) {
        double value = Double.valueOf(((EditText) getView().findViewById(R.id.edit_frame_rate)).getText().toString());
        Log.d("onClickOk", String.valueOf(value));
        int code = ((Spinner) getView().findViewById(R.id.spinner)).getSelectedItemPosition();
        Log.d("change code", String.valueOf(code));
        ValueForChange change = ValueForChange.getInstanceForCode(code);

        mValue = value;
        if ( mListener != null ) {
            mListener.onFrameRateChange(value, change);
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

        public void onFrameRateChange(double value, ValueForChange type);
    }

}
