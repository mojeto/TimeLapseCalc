package net.mojeto.timelapsecalc.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import net.mojeto.timelapsecalc.app.Duration;
import net.mojeto.timelapsecalc.app.R;
import net.mojeto.timelapsecalc.app.ValueForChange;
import net.mojeto.timelapsecalc.app.fragments.CameraFrameDurationFragment;
import net.mojeto.timelapsecalc.app.fragments.CameraRecordDurationFragment;
import net.mojeto.timelapsecalc.app.fragments.TimeSetFragment;

public class TimeSetActivity extends ActionBarActivity implements
        TimeSetFragment.OnTimeSetChangeListener{

    public static final String EXTRA_TIME = "time";
    public static final String EXTRA_RECOUNT = "recount";
    public static final String EXTRA_TYPE = "type";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_set);
        if (savedInstanceState == null) {
            Intent intent = getIntent();
            Duration time = new Duration(intent.getDoubleExtra(EXTRA_TIME, 1));
            ValueForChange recount = (ValueForChange) intent.getSerializableExtra(EXTRA_RECOUNT);
            ValueForChange type = (ValueForChange) intent.getSerializableExtra(EXTRA_TYPE);
            Fragment fragment;
            switch (type) {
                case CAMERA_FRAME_DURATION:
                    fragment = CameraFrameDurationFragment.newInstance(time, recount);
                    break;
                case CAMERA_RECORD_DURATION:
                    fragment = CameraRecordDurationFragment.newInstance(time, recount);
                    break;
                default:
                    setResult(RESULT_CANCELED);
                    finish();
                    return;
            }
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.time_set_frame, fragment)
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.time_set, menu);
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
    public void onTimeSetChange(Duration time, ValueForChange type, ValueForChange recount) {
        Intent result = new Intent();
        result.putExtra(EXTRA_TIME, time.getValue());
        result.putExtra(EXTRA_RECOUNT, recount);
        result.putExtra(EXTRA_TYPE, type);
        setResult(RESULT_OK, result);
        finish();
    }
}