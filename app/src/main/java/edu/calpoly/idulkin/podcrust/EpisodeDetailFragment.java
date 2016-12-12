package edu.calpoly.idulkin.podcrust;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import static com.google.android.gms.internal.zzs.TAG;

/**
 * A fragment representing a single Episode detail screen.
 * This fragment is either contained in a {@link EpisodeListActivity}
 * in two-pane mode (on tablets) or a {@link EpisodeDetailActivity}
 * on handsets.
 */
public class EpisodeDetailFragment extends ContractFragment<EpisodeDetailFragment.Contract> {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private String mp3;
    private String title;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public static EpisodeDetailFragment newInstance(String title, String mp3, String description, String image) {
        EpisodeDetailFragment edf = new EpisodeDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        bundle.putString("MP3", mp3);
        bundle.putString("DESCRIPTION", description);
        bundle.putString("IMAGE", image);
        edf.setArguments(bundle);
        return  edf;
    }


    private static final String TAG = "EpisodeDetailActivity";
    private MediaPlayerService mediaService;
    private boolean bound = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_episode_detail);
        //Bundle bundle = getIntent().getExtras();

        //title = bundle.getString("TITLE");
        //mp3 = bundle.getString("MP3");
        //description = bundle.getString("DESCRIPTION");
        //img = bundle.getString("IMAGE");


        //Floating action button plays the podcast
        FloatingActionButton fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(bound) {
                    Log.e("FAB click", "MP State:" + mediaService.getState());

                    switch (mediaService.getState()) {
                        case STOPPED:
                            mediaService.setSource(mp3);
                            mediaService.start();

                            fab.setImageBitmap(BitmapFactory.decodeResource(getActivity().getApplicationContext().getResources(),
                                    R.mipmap.ic_pause));
                            break;
                        case PAUSED:
                            mediaService.start();

                            fab.setImageBitmap(BitmapFactory.decodeResource(getActivity().getApplicationContext().getResources(),
                                    R.mipmap.ic_pause));
                            break;
                        case PLAYING:
                            mediaService.pause();

                            fab.setImageBitmap(BitmapFactory.decodeResource(getActivity().getApplicationContext().getResources(),
                                    R.mipmap.ic_play));
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
        Intent intent = new Intent(getActivity().getApplicationContext(), MediaPlayerService.class);
//        intent.putExtra("MP3", mp3);
        getActivity().bindService(intent, mConnection, Context.BIND_AUTO_CREATE);
        bound = true;
    }

    @Override
    public void onStop() {
        super.onStop();
        // Unbind from the service
        if (bound) {
            getActivity().unbindService(mConnection);
            bound = false;
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
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: Inflating views");
        View rootView = inflater.inflate(R.layout.episode_detail, container, false);

        Toolbar toolbar = (Toolbar) rootView.findViewById(R.id.detail_toolbar);
        toolbar.setTitle("" + this.getArguments().getString("TITLE"));

        ImageView iv = (ImageView) rootView.findViewById(R.id.image);
        Picasso.with(getActivity()).load(this.getArguments().getString("IMAGE")).into(iv);
        TextView desc = (TextView) rootView.findViewById(R.id.descriptionTV);
        desc.setText("" + this.getArguments().getString("DESCRIPTION"));

        // Show the dummy content as text in a TextView.
//        if (mItem != null) {
//            ((TextView) rootView.findViewById(R.id.episode_detail)).setText(mItem.details);
//        }

        return rootView;
    }

    public interface Contract {

    }

}
