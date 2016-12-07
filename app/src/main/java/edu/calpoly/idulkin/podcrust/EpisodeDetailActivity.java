package edu.calpoly.idulkin.podcrust;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

/**
 * An activity representing a single Episode detail screen. This
 * activity is only used narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link EpisodeListActivity}.
 */
public class EpisodeDetailActivity extends AppCompatActivity {

    private String title;
    private String mp3;
    private String description;
    private String img;
    private static final String TAG = "EpisodeDetailActivity";
    private MediaPlayerService mediaService;
    private boolean bound = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_detail);
        Bundle bundle = getIntent().getExtras();

        title = bundle.getString("TITLE");
        mp3 = bundle.getString("MP3");
        description = bundle.getString("DESCRIPTION");
        img = bundle.getString("IMAGE");
        Log.d(TAG, "onCreate: " + title + mp3 + description + img);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(myToolbar);

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
//            Bundle arguments = new Bundle();
//            arguments.putString(EpisodeDetailFragment.ARG_ITEM_ID,
//                    getIntent().getStringExtra(EpisodeDetailFragment.ARG_ITEM_ID));
//            EpisodeDetailFragment fragment = new EpisodeDetailFragment();
//            fragment.setArguments(arguments);
//            getSupportFragmentManager().beginTransaction()
//                    .add(R.id.episode_detail_container, fragment)
//                    .commit();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        EpisodeDetailFragment fragment = (EpisodeDetailFragment) fragmentManager.findFragmentById(R.id.episode_detail);

        if (fragment == null) {
            EpisodeDetailFragment edf = EpisodeDetailFragment.newInstance(title, mp3, description, img);
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.episode_detail, edf);
            fragmentTransaction.commit();
        }

        //Floating action button plays the podcast
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bound) {
                    Log.e("FAB click", "MP State:" + mediaService.getState());

                    switch (mediaService.getState()) {
                        case STOPPED:
                            mediaService.setSource(mp3);
                            mediaService.start();

                            fab.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                    R.mipmap.ic_pause_button));
                            break;
                        case PAUSED:
                            mediaService.start();

                            fab.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                    R.mipmap.ic_pause_button));
                            break;
                        case PLAYING:
                            mediaService.pause();

                            fab.setImageBitmap(BitmapFactory.decodeResource(getApplicationContext().getResources(),
                                    R.drawable.ic_play_button));
                            break;
                    }
                }
            }
        });

    }

    @Override
    public void onStart(){
        super.onStart();

        //Bind to the media player service
        Intent intent = new Intent(this, MediaPlayerService.class);
//        intent.putExtra("MP3", mp3);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        bound = true;
    }

    @Override
    protected void onStop() {
        super.onStop();
        // Unbind from the service
        if (bound) {
            unbindService(mConnection);
            bound = false;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, EpisodeListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            mediaService = binder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };


}
