package net.mojeto.timelapsecalc.app.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;

import net.mojeto.timelapsecalc.app.R;
import net.mojeto.timelapsecalc.app.ValueForChange;
import net.mojeto.timelapsecalc.app.fragments.FrameRateFragment;

public class FrameRateActivity extends ActionBarActivity implements
        FrameRateFragment.OnFrameRateChangeListener {

    public static final String EXTRA_FRAME_RATE = "frameRate";
    public static final String EXTRA_RECOUNT = "recount";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_frame_rate);
        Intent intent = getIntent();
        FrameRateFragment fragment = FrameRateFragment.newInstance(
                intent.getDoubleExtra(EXTRA_FRAME_RATE, 25),
                (ValueForChange) intent.getSerializableExtra(EXTRA_RECOUNT));

        getSupportFragmentManager().beginTransaction().replace(R.id.frame_rate_fragment, fragment)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.frame_rate, menu);
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
    public void onFrameRateChange(double value, ValueForChange type) {
        Intent result = new Intent();
        result.putExtra(EXTRA_FRAME_RATE, value);
        result.putExtra(EXTRA_RECOUNT, type);
        setResult(RESULT_OK, result);
        finish();
    }
}
