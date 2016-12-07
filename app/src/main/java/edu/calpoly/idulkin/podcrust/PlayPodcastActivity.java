package edu.calpoly.idulkin.podcrust;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

public class PlayPodcastActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;
    private Menu menu;
    private boolean play;

    private MediaPlayerService mediaService;
    private boolean bound = false;

    private static final String TAG = PlayPodcastActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_play_podcast);
        // tried to see if mp3 url from audiosear.ch worked
//        String url = "https://www.audiosear.ch/media/audio_file/10a9a/257457899-the-bill-simmons-podcast-ep-3-shackhouse-masters-preview-and-tiger-talk-with-bill-simmons.mp3";

        Toolbar myToolbar = (Toolbar) findViewById(R.id.playback_bar);
        setSupportActionBar(myToolbar);

    }

    @Override
    protected void onStart() {
        super.onStart();
        // Bind to LocalService
        String mp3, title;
        Intent i = getIntent();
        mp3 = i.getStringExtra("MP3");
        title = i.getStringExtra("TITLE");

        Intent intent = new Intent(this, MediaPlayerService.class);
        intent.putExtra("MP3", mp3);
        bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
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
    protected void onDestroy(){
        super.onDestroy();
    }

    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        this.menu = menu;

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.bottom_bar_menu, menu);



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_play:
                if (bound) {
                    // Call a method from the LocalService.
                    if (mediaService.getState() == MediaPlayerService.MP_STATE.PLAYING) {
                        mediaService.pause();
                        menu.getItem(0).setIcon(getDrawable(R.drawable.ic_play_button));
                    } else if ((mediaService.getState() == MediaPlayerService.MP_STATE.PAUSED) || (mediaService.getState() == MediaPlayerService.MP_STATE.STOPPED)) {
                        mediaService.start();
                        menu.getItem(0).setIcon(getDrawable(R.mipmap.ic_pause));
                    }
                }else{
                    Log.e("Playback", "Service not bound!");
                }

            default:
                // If we got here, the user's action was not recognized.
                // Invoke the superclass to handle it.
                    return super.onOptionsItemSelected(item);
                }
        }

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            MediaPlayerService.LocalBinder binder = (MediaPlayerService.LocalBinder) service;
            mediaService = binder.getService();

            mediaService.setSource("http://soundbible.com/grab.php?id=1851&type=mp3");
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };
}