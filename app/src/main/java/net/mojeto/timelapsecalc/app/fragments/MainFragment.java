package net.mojeto.timelapsecalc.app.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.mojeto.timelapsecalc.app.Camera;
import net.mojeto.timelapsecalc.app.Duration;
import net.mojeto.timelapsecalc.app.R;
import net.mojeto.timelapsecalc.app.ValueForChange;
import net.mojeto.timelapsecalc.app.Video;

import static java.lang.Math.round;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link net.mojeto.timelapsecalc.app.fragments.MainFragment.OnChangeValueListener} interface
 * to handle interaction events.
 */
public class MainFragment extends Fragment {

    private static final String ARG_CAMERA_FRAME_DURATION = "cameraFrameDuration";
    private static final String ARG_CAMERA_FRAMES = "cameraFrames";
    private static final String ARG_VIDEO_FRAME_DURATION = "videoFrameDuration";
    private static final String ARG_VIDEO_FRAMES = "videoFrames";

    private Video mVideo;
    private Camera mCamera;

    private OnChangeValueListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(ARG_VIDEO_FRAME_DURATION, mVideo.getFrameDuration().getValue());
        outState.putLong(ARG_VIDEO_FRAMES, mVideo.getSumOfFrames());
        outState.putDouble(ARG_CAMERA_FRAME_DURATION, mCamera.getFrameDuration().getValue());
        outState.putLong(ARG_CAMERA_FRAMES, mCamera.getSumOfFrames());

    }

    public void loadSaveInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mVideo = new Video(savedInstanceState.getDouble(ARG_VIDEO_FRAME_DURATION),
                    savedInstanceState.getLong(ARG_VIDEO_FRAMES));
            mCamera = new Camera(savedInstanceState.getDouble(ARG_CAMERA_FRAME_DURATION),
                    savedInstanceState.getLong(ARG_CAMERA_FRAMES));
        } else {
            mVideo = new Video(40l, 600);
            mCamera = new Camera(2000l, 600);
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadSaveInstanceState(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        Log.d("MainFragment", "onCreateView");

        view.findViewById(R.id.camera_frame_duration)
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        callChangeValue(mVideo, mCamera,
                                ValueForChange.CAMERA_FRAME_DURATION);
                    }
                });
        view.findViewById(R.id.camera_record_duration)
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        callChangeValue(mVideo, mCamera,
                                ValueForChange.CAMERA_RECORD_DURATION);
                    }
                });
        view.findViewById(R.id.camera_frames)
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        callChangeValue(mVideo, mCamera,
                                ValueForChange.CAMERA_FRAMES);
                    }
                });

        view.findViewById(R.id.video_frame_rate)
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        callChangeValue(mVideo, mCamera,
                                ValueForChange.VIDEO_FRAME_RATE);
                    }
                });

        view.findViewById(R.id.video_duration)
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        callChangeValue(mVideo, mCamera,
                                ValueForChange.VIDEO_DURATION);
                    }
                });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateValues(view, getResources());
    }

    public void callChangeValue(Video video, Camera camera,
                                ValueForChange change) {
        if (mListener != null) {
            mListener.onChangeValue(video, camera, change);
        }
    }

    public void updateValues(Video video, Camera camera) {
        mVideo = video;
        mCamera = camera;
        updateValues(getView(), getResources());
    }

    public void updateValues(View view, Resources r) {
        ((TextView) view.findViewById(R.id.camera_frame_duration))
                .setText(mCamera.getFrameDuration().format(r, Duration.Format.WITH_HOURS));

        ((TextView) view.findViewById(R.id.camera_record_duration))
                .setText(mCamera.getDuration().format(r, Duration.Format.WITH_DAYS));

        ((TextView) view.findViewById(R.id.camera_frames))
                .setText(String.format(r.getString(R.string.camera_frames_value),
                        mCamera.getSumOfFrames()));

        ((TextView) view.findViewById(R.id.video_frame_rate))
                .setText(String.format(r.getString(R.string.video_frame_rate_value),
                        round(mVideo.getFrameRate())));
                //.setText(String.valueOf(mVideo.getFrameRate()));

        ((TextView) view.findViewById(R.id.video_duration))
                .setText(mVideo.getDuration().format(r, Duration.Format.WITH_DAYS));
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        try {
            mListener = (OnChangeValueListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnChangeValueListener");
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
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnChangeValueListener {

        public void onChangeValue(Video video, Camera camera, ValueForChange type);
    }

}