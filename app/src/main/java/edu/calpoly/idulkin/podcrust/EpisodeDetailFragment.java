package edu.calpoly.idulkin.podcrust;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import edu.calpoly.idulkin.podcrust.dummy.DummyContent;

/**
 * A fragment representing a single Episode detail screen.
 * This fragment is either contained in a {@link EpisodeListActivity}
 * in two-pane mode (on tablets) or a {@link EpisodeDetailActivity}
 * on handsets.
 */
public class EpisodeDetailFragment extends Fragment {
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
    public static EpisodeDetailFragment newInstance(String title, String mp3) {
        EpisodeDetailFragment edf = new EpisodeDetailFragment();

        Bundle bundle = new Bundle();
        bundle.putString("TITLE", title);
        bundle.putString("MP3", mp3);
        edf.setArguments(bundle);
        return  edf;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        if (getArguments().containsKey(ARG_ITEM_ID)) {
//            // Load the dummy content specified by the fragment
//            // arguments. In a real-world scenario, use a Loader
//            // to load content from a content provider.
//            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
//
//            Activity activity = this.getActivity();
//            CollapsingToolbarLayout appBarLayout = (CollapsingToolbarLayout) activity.findViewById(R.id.toolbar_layout);
//            if (appBarLayout != null) {
//                appBarLayout.setTitle(mItem.content);
//            }
//        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.episode_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.episode_detail)).setText(mItem.details);
        }

        return rootView;
    }


}
