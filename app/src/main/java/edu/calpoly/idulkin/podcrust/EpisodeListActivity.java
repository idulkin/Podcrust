package edu.calpoly.idulkin.podcrust;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import audiosearch.Audiosearch;
import edu.calpoly.idulkin.podcrust.rest.Episode.Episode;
import edu.calpoly.idulkin.podcrust.rest.QueryExecutor;
import edu.calpoly.idulkin.podcrust.rest.ShowWithEpisodes;

/**
 * An activity representing a list of Episodes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link EpisodeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
public class EpisodeListActivity extends AppCompatActivity implements EpisodeDetailFragment.Contract{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private static final String TAG = "EpisodeListActivity";
    // temp data
//    private static Show showWithEpisodes;
//    private static Show.dummyEpisode[] list;
    private static SimpleItemRecyclerViewAdapter mAdapter;
    private static ShowWithEpisodes showWithEpisodes;
    private static ArrayList<Episode> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_list);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        Intent intent = getIntent();
        long show_id = intent.getLongExtra("SHOWID", -1);
        getQueryResults(show_id);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.episode_list);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // convert list to ArrayList
        mAdapter = new SimpleItemRecyclerViewAdapter(list);
        recyclerView.setAdapter(mAdapter);

        if (savedInstanceState == null) {
//            searchAudioList = new ArrayList<SearchAudio>();
        } else {
//            searchAudioList = savedInstanceState.getParcelableArrayList("SEARCHAUDIOLIST");
        }
        // showWithEpisodes Title temp
        TextView tv = (TextView) findViewById(R.id.showTitle);
        tv.setText(showWithEpisodes.show.getTitle());
        if (findViewById(R.id.episode_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void setupRecyclerView(@NonNull RecyclerView recyclerView) {
        recyclerView.setAdapter(new SimpleItemRecyclerViewAdapter(list));
    }

    private void getQueryResults (long show_id) {
        final String callbackUrl = "urn:ietf:wg:oauth:2.0:oob";
        final String applicationId = "c2b235f2620e362157a40aec609e737fe5a2547784933e00201ff90358e092c5";
        final String secret = "bee75fbb20ce6b45b64113b44208d12aeca02121fee8ea40f1bd9f44b491ba1c";
        final String authorizationCode = "ad2311b2860d224f89c32b7dfd4cb99550ba358aef412fae9ad11b52957a8930";

/*        new Thread(() -> {
            try {
                Audiosearch client = new Audiosearch()
                        .setApplicationId(applicationId)
                        .setSecret(secret)
                        .build();
                showWithEpisodes = new Show(client, show_id);
            } catch (Exception e) {
                Log.d("searchlist", "failure to search");
                Log.d("searchlist", e.toString());
            }
        }).start(); */
        Thread t = new Thread(() -> {
            try {
                Audiosearch client = new Audiosearch()
                        .setApplicationId(applicationId)
                        .setSecret(secret)
                        .build();
//                showWithEpisodes = new Show(client, show_id);
                showWithEpisodes = QueryExecutor.getShowWithEpisodes(client, show_id);
                list = showWithEpisodes.episodes;
            } catch (Exception e) {
                Log.d("searchlist", "failure to search");
                Log.d("searchlist", e.toString());
            }
        });
        t.start();
        // t.join() is to wait for thread to finish
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        list = showWithEpisodes.getList();
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private ArrayList<Episode> mValues;

        public SimpleItemRecyclerViewAdapter(ArrayList<Episode> episodes) {
            mValues = episodes;
        }
// work on layout episode_list_content
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.episode_list_content, parent, false);
            return new ViewHolder(view);
        }


        // work on episode_detail_container
        // check out episode_detail_container and be able to showWithEpisodes what you want to showWithEpisodes
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.bind(mValues.get(position));
            holder.mItem = mValues.get(position);

            holder.mIdView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: CLICKED");
                    if (mTwoPane) {
                        Bundle arguments = new Bundle();
                        arguments.putString("TITLE", holder.mItem.getTitle());
                        arguments.putString("MP3", holder.mItem.getAudioFiles().get(0).getUrl());
                        arguments.putString("DESCRIPTION", holder.mItem.getDescription());
                        arguments.putString("IMAGE", showWithEpisodes.show.getImageFiles().get(0).getUrl().getFull());
                        EpisodeDetailFragment fragment = new EpisodeDetailFragment();
                        fragment.setArguments(arguments);
                        getSupportFragmentManager().beginTransaction()
                                .replace(R.id.episode_detail_container, fragment)
                                .commit();
                    } else {
                        Context context = v.getContext();
                        Intent intent = new Intent(context, EpisodeDetailActivity.class);
                        intent.putExtra("TITLE", holder.mItem.getTitle());
                        intent.putExtra("MP3", holder.mItem.getAudioFiles().get(0).getUrl());
                        intent.putExtra("DESCRIPTION", holder.mItem.getDescription());
                        intent.putExtra("IMAGE", showWithEpisodes.show.getImageFiles().get(0).getUrl().getFull());
                        context.startActivity(intent);
                    }
                }
            });
        }

        @Override
        public int getItemViewType(int position) {
            return R.layout.episode_list_content;
        }

        @Override
        public int getItemCount() {
            if (list == null) {
                return 0;
            }
            return list.size();
        }
        // Recyclerview viewholder should hold
        // Title
        // which layout is this using
        public class ViewHolder extends RecyclerView.ViewHolder {
            public Episode mItem;
            public final TextView mIdView;

            public ViewHolder(View view) {
                super(view);
                mIdView = (TextView) view.findViewById(R.id.id);
            }

            @Override
            public String toString() {
                return super.toString() + " '" + mIdView.getText() + "'";
            }

            public void bind(Episode episode) {
                mItem = episode;
                mIdView.setText(mItem.getTitle());
            }
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }
}
