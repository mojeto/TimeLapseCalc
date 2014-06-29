package net.mojeto.timelapsecalc.app.fragments;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import net.mojeto.timelapsecalc.app.Duration;
import net.mojeto.timelapsecalc.app.R;
import net.mojeto.timelapsecalc.app.ValueForChange;

/**
 * Created by honza on 26.6.14.
 */
public abstract class TimeSetFragment extends Fragment {

    public abstract int recountToSpinnerPosition(ValueForChange recount);
    public abstract ValueForChange spinnerPositionToRecount(int position);
    public abstract String[] getSpinnerItems();
    public abstract CharSequence getEditText();
    public abstract ValueForChange getValueType();
    public abstract boolean isShowDays();

    private static final String ARG_TIME = "time";
    private static final String ARG_RECOUNT = "recount";

    protected Duration mTime;
    protected int mPosition = 0;

    protected OnTimeSetChangeListener mListener;

    protected static void setArguments(TimeSetFragment fragment, Duration time,
                                       ValueForChange recount) {
        Bundle args = new Bundle();
        args.putDouble(ARG_TIME, time.getValue());
        args.putInt(ARG_RECOUNT, fragment.recountToSpinnerPosition(recount));
        fragment.setArguments(args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mTime = new Duration(getArguments().getDouble(ARG_TIME));
            mPosition = getArguments().getInt(ARG_RECOUNT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_time_set, container, false);

        long days = isShowDays() ? mTime.getDays(true) : 0l;
        boolean isShowDays = days > 0;

        if (isShowDays) {
            ((TextView) view.findViewById(R.id.edit_days_label)).setVisibility(View.VISIBLE);

            EditText text = ((EditText) view.findViewById(R.id.edit_days));
            text.setVisibility(View.VISIBLE);
            text.setText(String.valueOf(days));
        }

        ((EditText) view.findViewById(R.id.edit_hours))
                .setText(String.valueOf(mTime.getHours(!isShowDays)));

        ((EditText) view.findViewById(R.id.edit_minutes))
                .setText(String.valueOf(mTime.getMinutes()));

        ((EditText) view.findViewById(R.id.edit_seconds))
                .setText(String.valueOf(mTime.getSeconds()));

        ((EditText) view.findViewById(R.id.edit_milliseconds))
                .setText(String.valueOf(mTime.getMilliseconds()));

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

        ((TextView) view.findViewById(R.id.edit_msg)).setText(getEditText());

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_spinner_item,
                getSpinnerItems());
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Spinner spinner = ((Spinner) view.findViewById(R.id.spinner));
        spinner.setAdapter(adapter);
        spinner.setSelection(mPosition);
        return view;
    }

    public void onClickCancel(View view) {
        if (mListener != null) {
            mListener.onCancel();
        }
    }

    protected int getValueOf(int id) throws NumberFormatException {
        String value = ((EditText) getView().findViewById(id)).getText().toString();
        try {
            return value.equals("") ? 0 : Integer.valueOf(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException(value);
        }
    }

    public void onClickOk(View view) {

        String value = "";
        mTime = new Duration();
        try {
            if (isShowDays()) {
                mTime = mTime.setDays(getValueOf(R.id.edit_days));
            }
            mTime = mTime.setHours(getValueOf(R.id.edit_hours))
                    .setMinutes(getValueOf(R.id.edit_minutes))
                    .setSeconds(getValueOf(R.id.edit_seconds))
                    .setMilliseconds(getValueOf(R.id.edit_milliseconds));
        } catch (NumberFormatException e) {
            Toast.makeText(getActivity(),
                    String.format(getResources().getString(R.string.repair_value), e.getMessage()),
                    Toast.LENGTH_LONG).show();
            return;
        }
        mPosition = ((Spinner) getView().findViewById(R.id.spinner)).getSelectedItemPosition();

        if ( mListener != null ) {
            mListener.onTimeSetChange(mTime, getValueType(), spinnerPositionToRecount(mPosition));
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnTimeSetChangeListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnTimeSetChangeListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    public interface OnTimeSetChangeListener {

        public void onCancel();
        public void onTimeSetChange(Duration time, ValueForChange type, ValueForChange recount);
    }
}
