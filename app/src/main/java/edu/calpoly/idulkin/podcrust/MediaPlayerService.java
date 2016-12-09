package edu.calpoly.idulkin.podcrust;


import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.wifi.WifiManager;
import android.os.Binder;
import android.os.IBinder;
import android.os.PowerManager;
import android.util.Log;

import java.io.IOException;

/**
 * Created by igor.dulkin on 11/24/16.
 */

public class MediaPlayerService extends Service
        implements MediaPlayer.OnPreparedListener, MediaPlayer.OnErrorListener, AudioManager.OnAudioFocusChangeListener{

    MediaPlayer mediaPlayer = null;

    private static final String ACTION_PLAY = "com.example.action.PLAY";
    private String url = "http://soundbible.com/grab.php?id=1851&type=mp3";
    private WifiManager.WifiLock wifiLock;
    private AudioManager audioManager;
    private String TAG = "Media Player Service";

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        MediaPlayerService getService() {
            return MediaPlayerService.this;
        }
    }

    //Tracks the state of the MediaPlayer
    public enum MP_STATE {
        PLAYING, PAUSED, STOPPED
    }
    private MP_STATE state;

    public MediaPlayerService(){}

    @Override
    public void onCreate(){
        super.onCreate();

        initMediaPlayer();
        Log.d(TAG, "Starting service");

        //Keeps using the wifi to stream if the device goes to sleep
        wifiLock = ((WifiManager) getSystemService(Context.WIFI_SERVICE))
                .createWifiLock(WifiManager.WIFI_MODE_FULL, "PodcrustLock");
        wifiLock.acquire();

        //Creates a Notification to run this as a foreground service
        PendingIntent pi = PendingIntent.getActivity(getApplicationContext(), 0,
                new Intent(getApplicationContext(), MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT); //This intent is to get the current status of this service for the notification. Not using it yet.

        Notification CrustNotification = new Notification.Builder(getApplicationContext())
                .setContentTitle("PODCRUST PODCRUST PODCRUST")
                .setContentText("podcrust podcrust podcrust")
                .setSmallIcon(R.drawable.ic_play_button)
                .build();

        startForeground(11, CrustNotification);

        //Initializes AudioManager, for handling audio focus
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        int result = audioManager.requestAudioFocus(this, AudioManager.STREAM_MUSIC,
                AudioManager.AUDIOFOCUS_GAIN);

        if (result != AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
            // could not get audio focus.
        }

    }

    public IBinder onBind(Intent intent) {
        return mBinder;
    }

    /** Called when MediaPlayer is ready */
    public void onPrepared(MediaPlayer player) {
        player.start();
    }

    public void initMediaPlayer() {
        mediaPlayer = new MediaPlayer();
        mediaPlayer.setOnPreparedListener(this);

        mediaPlayer.setOnErrorListener(this);
        mediaPlayer.setWakeMode(getApplicationContext(), PowerManager.PARTIAL_WAKE_LOCK);

        setState(MP_STATE.STOPPED);
    }

    public MP_STATE getState(){
        return state;
    }

    public void setState(MP_STATE state){
        this.state = state;
    }

    public void start(){
        mediaPlayer.start();
        setState(MP_STATE.PLAYING);
        Log.e("Media Player Service", "State: "+ getState());
    }

    public void pause(){
        mediaPlayer.pause();
        setState(MP_STATE.PAUSED);

        Log.e("Media Player Service", "State: "+ getState());
    }

    public void stop(){
        mediaPlayer.stop();
        setState(MP_STATE.STOPPED);

        Log.e("Media Player Service", "State: "+ getState());
    }

    public void setSource(String source){
        try{
            mediaPlayer.setDataSource(source);
        }catch(IOException e){
            Log.e("Media Player Service:", "Failed to open media stream from URL");
        }
        mediaPlayer.prepareAsync(); // prepare async to not block main thread

        this.url = source;
    }

    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        // ... react appropriately ...
        // The MediaPlayer has moved to the Error state, must be reset!
//        initMediaPlayer();

        return true;
    }

    public void onAudioFocusChange(int focusChange) {
        switch (focusChange) {
            case AudioManager.AUDIOFOCUS_GAIN:
                // resume playback
                if (mediaPlayer == null) initMediaPlayer();
                else if (state == MP_STATE.STOPPED) start();
                mediaPlayer.setVolume(1.0f, 1.0f);
                break;

            case AudioManager.AUDIOFOCUS_LOSS:
                // Lost focus for an unbounded amount of time: stop playback and release media player
                if (state == MP_STATE.PLAYING) stop();
                mediaPlayer.release();
                mediaPlayer = null;
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                // Lost focus for a short time, but we have to stop
                // playback. We don't release the media player because playback
                // is likely to resume
                if (state == MP_STATE.PLAYING) pause();
                break;

            case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                // Lost focus for a short time, but it's ok to keep playing
                // at an attenuated level
                if (mediaPlayer.isPlaying()) mediaPlayer.setVolume(0.1f, 0.1f);
                break;
        }
    }

    @Override
    public void onDestroy(){
        super.onDestroy();

        stop();
        if(mediaPlayer == null) mediaPlayer.release();

        wifiLock.release();

        stopForeground(true);
        stopSelf();
    }
}


