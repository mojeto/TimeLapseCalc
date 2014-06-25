package net.mojeto.timelapsecalc.app.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import net.mojeto.timelapsecalc.app.Camera;
import net.mojeto.timelapsecalc.app.Duration;
import net.mojeto.timelapsecalc.app.FrameSet;
import net.mojeto.timelapsecalc.app.R;
import net.mojeto.timelapsecalc.app.ValueForChange;
import net.mojeto.timelapsecalc.app.Video;
import net.mojeto.timelapsecalc.app.fragments.FrameRateFragment;
import net.mojeto.timelapsecalc.app.fragments.MainFragment;

public class MainActivity extends ActionBarActivity implements MainFragment.OnChangeValueListener,
        FrameRateFragment.OnFrameRateChangeListener {

    private static final String ARG_CAMERA_FRAME_DURATION = "cameraFrameDuration";
    private static final String ARG_CAMERA_FRAMES = "cameraFrames";
    private static final String ARG_VIDEO_FRAME_DURATION = "videoFrameDuration";
    private static final String ARG_VIDEO_FRAMES = "videoFrames";

    Video mVideo;
    Camera mCamera;

    /**
     * Save all appropriate fragment state.
     *
     * @param outState
     */
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putDouble(ARG_VIDEO_FRAME_DURATION, mVideo.getFrameDuration().getValue());
        outState.putLong(ARG_VIDEO_FRAMES, mVideo.getSumOfFrames());
        outState.putDouble(ARG_CAMERA_FRAME_DURATION, mCamera.getFrameDuration().getValue());
        outState.putLong(ARG_CAMERA_FRAMES, mCamera.getSumOfFrames());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (savedInstanceState != null) {
            mVideo = new Video(savedInstanceState.getDouble(ARG_VIDEO_FRAME_DURATION),
                    savedInstanceState.getLong(ARG_VIDEO_FRAMES));
            mCamera = new Camera(savedInstanceState.getDouble(ARG_CAMERA_FRAME_DURATION),
                    savedInstanceState.getLong(ARG_CAMERA_FRAMES));
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onChangeValue(Video video, Camera camera, ValueForChange type) {
        mVideo = video;
        mCamera = camera;
        boolean useFragment = findViewById(R.id.edit_column) != null;
        Fragment fragment = null;
        switch (type) {
            case VIDEO_FRAME_RATE:
                if (useFragment)
                    fragment = (Fragment) FrameRateFragment.newInstance(video.getFrameRate(),
                            ValueForChange.VIDEO_DURATION);
                break;
            default:
                return;
        }
        if ( fragment != null ) {
            getSupportFragmentManager().beginTransaction().replace(R.id.edit_column, fragment)
                    .commit();
        }

    }

    @Override
    public void onFrameRateChange(double value, ValueForChange change) {
        Log.d("MainActivity.onFrameRateChange", String.valueOf(value));
        Duration videoDuration;
        switch (change) {
            case CAMERA_FRAME_DURATION:
                //set video frame rate change sum of frames
                videoDuration = mVideo.getDuration();
                mVideo.setFrameRate(value);
                mVideo.setDuration(videoDuration, FrameSet.Change.SUM_OF_FRAMES);

                //set camera sum of frames change frame duration
                Duration cameraDuration = mCamera.getDuration();
                mCamera.setSumOfFrames(mVideo.getSumOfFrames());
                mCamera.setDuration(cameraDuration, FrameSet.Change.FRAME_DURATION);
                break;
            case CAMERA_RECORD_DURATION:
                //set video frame rate change sum of frames
                videoDuration = mVideo.getDuration();
                mVideo.setFrameRate(value);
                mVideo.setDuration(videoDuration, FrameSet.Change.SUM_OF_FRAMES);

                //set camera sum of frames, change frame rate
                mCamera.setSumOfFrames(mVideo.getSumOfFrames());
                break;
            case VIDEO_DURATION:
            default:
                mVideo.setFrameRate(value);
                break;
        }

        ((MainFragment) getSupportFragmentManager().findFragmentById(R.id.main_fragment))
                .updateValues(mVideo, mCamera);
    }
}
