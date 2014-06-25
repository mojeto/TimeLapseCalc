package net.mojeto.timelapsecalc.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import net.mojeto.timelapsecalc.app.R;
import net.mojeto.timelapsecalc.app.ValueForChange;
import net.mojeto.timelapsecalc.app.fragments.SumOfFramesFragment;

public class SumOfFramesActivity extends ActionBarActivity implements
        SumOfFramesFragment.OnSumOfFramesChangeListener {

    public static final String EXTRA_SUM_OF_FRAMES = "sumOfFrames";
    public static final String EXTRA_CAMERA_RECOUNT = "cameraRecount";
    public static final String EXTRA_VIDEO_RECOUNT = "videoRecount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sum_of_frames);
        Intent intent = getIntent();
        SumOfFramesFragment fragment = SumOfFramesFragment.newInstance(
                intent.getLongExtra(EXTRA_SUM_OF_FRAMES, 1),
                (ValueForChange) intent.getSerializableExtra(EXTRA_CAMERA_RECOUNT),
                (ValueForChange) intent.getSerializableExtra(EXTRA_VIDEO_RECOUNT));

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.sum_of_frames_fragment, fragment)
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.sum_of_frames, menu);
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
    public void onCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    @Override
    public void onSumOfFramesChange(long sumOfFrames, ValueForChange cameraRecount,
                                    ValueForChange videoRecount) {
        Intent result = new Intent();
        result.putExtra(EXTRA_SUM_OF_FRAMES, sumOfFrames);
        result.putExtra(EXTRA_CAMERA_RECOUNT, cameraRecount);
        result.putExtra(EXTRA_VIDEO_RECOUNT, videoRecount);
        setResult(RESULT_OK, result);
        finish();
    }
}
