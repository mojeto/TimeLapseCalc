package net.mojeto.timelapsecalc.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import net.mojeto.timelapsecalc.app.ChangeValue;
import net.mojeto.timelapsecalc.app.R;
import net.mojeto.timelapsecalc.app.ValueForChange;
import net.mojeto.timelapsecalc.app.fragments.FrameRateFragment;
import net.mojeto.timelapsecalc.app.fragments.MainFragment;
import net.mojeto.timelapsecalc.app.fragments.SumOfFramesFragment;

public class MainActivity extends ActionBarActivity implements MainFragment.OnChangeValueListener,
        FrameRateFragment.OnFrameRateChangeListener, SumOfFramesFragment.OnSumOfFramesChangeListener {

    private static final int REQUEST_VIDEO_FRAME_RATE = 1;
    private static final int REQUEST_CAMERA_SUM_OF_FRAMES = 2;

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

    public boolean useEditFragment() {
        return findViewById(R.id.edit_column) != null;
    }

    public ChangeValue getChangeValue() {
        return (ChangeValue) getSupportFragmentManager().findFragmentById(R.id.main_fragment);
    }

    public void setEditFragment(Fragment fragment) {
        if ( fragment != null ) {
            getSupportFragmentManager().beginTransaction().replace(R.id.edit_column, fragment)
                    .commit();
        }
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case REQUEST_VIDEO_FRAME_RATE:
                if(resultCode != RESULT_OK)
                    return;
                onFrameRateChange(data.getDoubleExtra(FrameRateActivity.EXTRA_FRAME_RATE, 25),
                        (ValueForChange) data.getSerializableExtra(FrameRateActivity.EXTRA_RECOUNT));
                break;
            case REQUEST_CAMERA_SUM_OF_FRAMES:
                if(resultCode != RESULT_OK)
                    return;
                onSumOfFramesChange(data.getLongExtra(SumOfFramesActivity.EXTRA_SUM_OF_FRAMES, 1),
                        (ValueForChange) data.getSerializableExtra(
                                SumOfFramesActivity.EXTRA_CAMERA_RECOUNT),
                        (ValueForChange) data.getSerializableExtra(
                                SumOfFramesActivity.EXTRA_VIDEO_RECOUNT));
                break;
            default:
                super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onFrameRateChange(double value, ValueForChange change) {
        getChangeValue().setVideoFrameRate(value, change);
        clearEditColumn();
    }

    //Implements MainFragment.OnChangeValueListener methods

    @Override
    public void onChangeVideoFrameRateClick(double frameRate) {
        ValueForChange recount = ValueForChange.VIDEO_DURATION;
        if ( useEditFragment() ) {
            setEditFragment(FrameRateFragment.newInstance(frameRate, recount));
        } else {
            Intent intent = new Intent(this, FrameRateActivity.class);
            intent.putExtra(FrameRateActivity.EXTRA_FRAME_RATE, frameRate);
            intent.putExtra(FrameRateActivity.EXTRA_RECOUNT, recount);
            startActivityForResult(intent, REQUEST_VIDEO_FRAME_RATE);
        }
    }

    @Override
    public void onChangeCameraSumOfFramesClick(long sumOfFrames) {
        ValueForChange cameraRecount = ValueForChange.CAMERA_RECORD_DURATION;
        ValueForChange videoRecount = ValueForChange.VIDEO_DURATION;
        if ( useEditFragment() ) {
            setEditFragment(SumOfFramesFragment.newInstance(sumOfFrames, cameraRecount,
                    videoRecount));
        } else {
            Intent intent = new Intent(this, SumOfFramesActivity.class);
            intent.putExtra(SumOfFramesActivity.EXTRA_SUM_OF_FRAMES, sumOfFrames);
            intent.putExtra(SumOfFramesActivity.EXTRA_CAMERA_RECOUNT, cameraRecount);
            intent.putExtra(SumOfFramesActivity.EXTRA_VIDEO_RECOUNT, videoRecount);
            startActivityForResult(intent, REQUEST_CAMERA_SUM_OF_FRAMES);
        }
    }

    @Override
    public void onCancel() {
        clearEditColumn();
    }

    public void clearEditColumn() {
        if ( useEditFragment() ) {
            FragmentManager fm = getSupportFragmentManager();
            fm.beginTransaction().remove(fm.findFragmentById(R.id.edit_column)).commit();
        }
    }

    @Override
    public void onSumOfFramesChange(long sumOfFrames, ValueForChange cameraRecount,
                                    ValueForChange videoRecount) {
        getChangeValue().setCameraSumOfFrames(sumOfFrames, cameraRecount, videoRecount);
        clearEditColumn();
    }
}
