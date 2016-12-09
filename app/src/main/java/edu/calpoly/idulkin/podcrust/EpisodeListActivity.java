package edu.calpoly.idulkin.podcrust;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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

/**
 * An activity representing a episodes of Episodes. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a episodes of items, which when touched,
 * lead to a {@link EpisodeDetailActivity} representing
 * item details. On tablets, the activity presents the episodes of items and
 * item details side-by-side using two vertical panes.
 */
public class EpisodeListActivity extends AppCompatActivity implements EpisodeDetailFragment.Contract{

    /**
     * Whether or not the activity is in two-pane mode, i.e. running on a tablet
     * device.
     */
    private boolean mTwoPane;
    private static final String TAG = "EpisodeListActivity";
    private SimpleItemRecyclerViewAdapter mAdapter;
    private  String imageurl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episode_list);

        final ArrayList<Episode> episodes = new ArrayList<>();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());

        Intent intent = getIntent();
        TextView tv = (TextView) findViewById(R.id.showTitle);
        tv.setText(intent.getStringExtra("TITLE"));
        imageurl = intent.getStringExtra("IMAGEURL");

        long show_id = intent.getLongExtra("SHOWID", -1);
        getQueryResults(show_id);

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.episode_list);
        assert recyclerView != null;
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        // convert episodes to ArrayList
        mAdapter = new SimpleItemRecyclerViewAdapter(episodes);
        recyclerView.setAdapter(mAdapter);

        if (savedInstanceState == null) {
//            searchAudioList = new ArrayList<SearchAudio>();
        } else {
//            searchAudioList = savedInstanceState.getParcelableArrayList("SEARCHAUDIOLIST");
        }

        if (findViewById(R.id.episode_detail_container) != null) {
            // The detail container view will be present only in the
            // large-screen layouts (res/values-w900dp).
            // If this view is present, then the
            // activity should be in two-pane mode.
            mTwoPane = true;
        }
    }

    private void getQueryResults (long show_id) {
        Thread t = new Thread(() -> {
            int aasdfa = 3;
            try {
                Audiosearch client = QueryExecutor.createClient();

                QueryExecutor.getShowWithEpisodesAsync(client, show_id, showWithEpisodes -> {
                        runOnUiThread(() -> {
                            TextView tv = (TextView) findViewById(R.id.showTitle);
                            tv.setText(showWithEpisodes.show.getTitle());
                            mAdapter.updateEpisodes(showWithEpisodes.episodes);
                            mAdapter.notifyDataSetChanged();
                        });
                });
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
    }

    public class SimpleItemRecyclerViewAdapter
            extends RecyclerView.Adapter<SimpleItemRecyclerViewAdapter.ViewHolder> {

        private ArrayList<Episode> mValues;

        protected void updateEpisodes(ArrayList<Episode> episodes) {
            mValues = episodes;
        }

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
                        arguments.putString("IMAGE", imageurl);
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
                        intent.putExtra("IMAGE", imageurl);
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
            if (mValues == null) {
                return 0;
            }
            return mValues.size();
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
