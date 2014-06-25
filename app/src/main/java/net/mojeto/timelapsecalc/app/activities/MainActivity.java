package net.mojeto.timelapsecalc.app.activities;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import net.mojeto.timelapsecalc.app.ChangeValue;
import net.mojeto.timelapsecalc.app.R;
import net.mojeto.timelapsecalc.app.ValueForChange;
import net.mojeto.timelapsecalc.app.fragments.FrameRateFragment;
import net.mojeto.timelapsecalc.app.fragments.MainFragment;

public class MainActivity extends ActionBarActivity implements MainFragment.OnChangeValueListener,
        FrameRateFragment.OnFrameRateChangeListener {

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

    @Override
    public void onFrameRateChange(double value, ValueForChange change) {
        getChangeValue().setVideoFrameRate(value, change);
    }

    //Implements MainFragment.OnChangeValueListener methods

    @Override
    public void onChangeVideoFrameRateClick(double frameRate) {
        if ( useEditFragment() ) {
            setEditFragment(FrameRateFragment.newInstance(frameRate,
                    ValueForChange.VIDEO_DURATION));
        } else {
            //TODO start edition activity
            //callEditActivity();
        }
    }
}
