package net.mojeto.timelapsecalc.app.fragments;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.mojeto.timelapsecalc.app.Camera;
import net.mojeto.timelapsecalc.app.ChangeValue;
import net.mojeto.timelapsecalc.app.Duration;
import net.mojeto.timelapsecalc.app.R;
import net.mojeto.timelapsecalc.app.TimeLapseCalc;
import net.mojeto.timelapsecalc.app.ValueForChange;
import net.mojeto.timelapsecalc.app.Video;

import static java.lang.Math.round;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link net.mojeto.timelapsecalc.app.fragments.MainFragment.OnChangeValueListener} interface
 * to handle interaction events.
 */
public class MainFragment extends Fragment implements ChangeValue{

    private static final String ARG_CAMERA_FRAME_DURATION = "cameraFrameDuration";
    private static final String ARG_CAMERA_FRAMES = "cameraFrames";
    private static final String ARG_VIDEO_FRAME_DURATION = "videoFrameDuration";
    private static final String ARG_VIDEO_FRAMES = "videoFrames";

    private TimeLapseCalc mCalc;

    private OnChangeValueListener mListener;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Video video = mCalc.getVideo();
        Camera camera = mCalc.getCamera();
        outState.putDouble(ARG_VIDEO_FRAME_DURATION, video.getFrameDuration().getValue());
        outState.putLong(ARG_VIDEO_FRAMES, video.getSumOfFrames());
        outState.putDouble(ARG_CAMERA_FRAME_DURATION, camera.getFrameDuration().getValue());
        outState.putLong(ARG_CAMERA_FRAMES, camera.getSumOfFrames());

    }

    public void loadSaveInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mCalc = new TimeLapseCalc(
                        new Camera(savedInstanceState.getDouble(ARG_CAMERA_FRAME_DURATION),
                                   savedInstanceState.getLong(ARG_CAMERA_FRAMES)),
                        new Video(savedInstanceState.getDouble(ARG_VIDEO_FRAME_DURATION),
                                  savedInstanceState.getLong(ARG_VIDEO_FRAMES)));
        } else {
            mCalc = new TimeLapseCalc(new Camera(2000l, 600), new Video(40l, 600));
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

//        view.findViewById(R.id.camera_frame_duration)
//                .setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        callChangeValue(mVideo, mCamera,
//                                ValueForChange.CAMERA_FRAME_DURATION);
//                    }
//                });
//        view.findViewById(R.id.camera_record_duration)
//                .setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        callChangeValue(mVideo, mCamera,
//                                ValueForChange.CAMERA_RECORD_DURATION);
//                    }
//                });
        view.findViewById(R.id.camera_frames)
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        callChangeCameraFrames(mCalc.getCamera().getSumOfFrames());
                    }
                });
//
        view.findViewById(R.id.video_frame_rate)
                .setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        callChangeFrameRate(mCalc.getVideo().getFrameRate());
                    }
                });
//
//        view.findViewById(R.id.video_duration)
//                .setOnClickListener(new View.OnClickListener() {
//                    public void onClick(View v) {
//                        callChangeValue(mVideo, mCamera,
//                                ValueForChange.VIDEO_DURATION);
//                    }
//                });

        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        updateValues(view, getResources(), mCalc.getCamera(), mCalc.getVideo());
    }

    public void callChangeCameraFrames(long sumOfFrames) {
        if ( mListener != null ) {
            mListener.onChangeCameraSumOfFramesClick(sumOfFrames);
        }
    }

    public void callChangeFrameRate(double frameRate) {
        if (mListener != null) {
            mListener.onChangeVideoFrameRateClick(frameRate);
        }
    }

    @Override
    public void setVideoFrameRate(double frameRate, ValueForChange recount) {
        switch (recount) {
            case CAMERA_FRAME_DURATION:
                mCalc.setVideoFrameRateChangeCameraFrameDuration(frameRate);
                break;
            case CAMERA_RECORD_DURATION:
                mCalc.setVideoFrameRateChangeCameraDuration(frameRate);
            case VIDEO_DURATION:
            default:
                mCalc.setVideoFrameRateChangeVideoDuration(frameRate);
                break;
        }
        updateValues(mCalc.getCamera(), mCalc.getVideo());
    }

    @Override
    public void setCameraSumOfFrames(long sumOfFrames, ValueForChange cameraRecount,
                                     ValueForChange videoRecount) {
        switch (cameraRecount) {
            case CAMERA_FRAME_DURATION:
                if (videoRecount == ValueForChange.VIDEO_FRAME_RATE) {
                    mCalc.setCameraSumChangeCameraAndVideoFrameDuration(sumOfFrames);
                } else {
                    mCalc.setCameraSumChangeCameraFrameDurationAndVideoDuration(sumOfFrames);
                }
                break;
            case CAMERA_RECORD_DURATION:
            default:
                if (videoRecount == ValueForChange.VIDEO_FRAME_RATE) {
                    mCalc.setCameraSumChangeCameraDurationAndVideoFrameDuration(sumOfFrames);
                } else {
                    mCalc.setCameraSumChangeCameraAndVideoDuration(sumOfFrames);
                }
                break;
        }
        updateValues(mCalc.getCamera(), mCalc.getVideo());
    }

    public void updateValues(Camera camera, Video video) {
        updateValues(getView(), getResources(), camera, video);
    }

    public void updateValues(View view, Resources r, Camera camera, Video video) {
        ((TextView) view.findViewById(R.id.camera_frame_duration))
                .setText(camera.getFrameDuration().format(r, Duration.Format.WITH_HOURS));

        ((TextView) view.findViewById(R.id.camera_record_duration))
                .setText(camera.getDuration().format(r, Duration.Format.WITH_DAYS));

        ((TextView) view.findViewById(R.id.camera_frames))
                .setText(String.format(r.getString(R.string.camera_frames_value),
                        camera.getSumOfFrames()));

        ((TextView) view.findViewById(R.id.video_frame_rate))
                .setText(String.format(r.getString(R.string.video_frame_rate_value),
                        round(video.getFrameRate())));

        ((TextView) view.findViewById(R.id.video_duration))
                .setText(video.getDuration().format(r, Duration.Format.WITH_DAYS));
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

        public void onChangeVideoFrameRateClick(double frameRate);

        public void onChangeCameraSumOfFramesClick(long sumOfFrames);
    }

}