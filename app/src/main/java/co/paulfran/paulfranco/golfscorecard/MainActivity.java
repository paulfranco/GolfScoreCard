package co.paulfran.paulfranco.golfscorecard;

import android.app.ListActivity;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import co.paulfran.paulfranco.golfscorecard.Hole;
import co.paulfran.paulfranco.golfscorecard.ListAdapter;
import co.paulfran.paulfranco.golfscorecard.R;

public class MainActivity extends ListActivity {

    private static final String PREFS_FILE = "co.paulfran.paulfranco.golfscorecard.preferences";
    private static final String KEY_STROKECOUNT = "key_strokecount";
    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;
    private Hole[] mHoles = new Hole[18];
    private ListAdapter mListSAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mSharedPreferences = getSharedPreferences(PREFS_FILE, Context.MODE_PRIVATE);
        mEditor = mSharedPreferences.edit();

        // Initialize holes
        int strokes = 0;
        for (int i = 0; i < mHoles.length; i++) {
            // Retrive shared preferences
            strokes = mSharedPreferences.getInt(KEY_STROKECOUNT + i, 0);
            mHoles[i] = new Hole("Hole " + (i +1 ) + " :", strokes);
        }

        mListSAdapter = new ListAdapter(this, mHoles);
        setListAdapter(mListSAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Infate the menue; This adds items to the action
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_clear_strokes) {
            // add code to be executed when button is pressed
            // clear the values
            mEditor.clear();
            // save
            mEditor.apply();
            // For each loop
            for (Hole hole : mHoles) {
                hole.setStrokeCount(0);
            }
            // Notify adapter that the data has changed so that it clears the values when button is pressed
            mListSAdapter.notifyDataSetChanged();

            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // save values in shared preferences
        for (int i = 0; i < mHoles.length; i++) {
            mEditor.putInt(KEY_STROKECOUNT + i, mHoles[i].getStrokeCount());
        }
        mEditor.apply();
    }
}
