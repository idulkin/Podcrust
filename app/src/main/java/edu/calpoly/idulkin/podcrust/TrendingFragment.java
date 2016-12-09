package edu.calpoly.idulkin.podcrust;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.util.List;

import audiosearch.Audiosearch;
import audiosearch.model.TrendResult;
import edu.calpoly.idulkin.podcrust.rest.QueryExecutor;
import edu.calpoly.idulkin.podcrust.rest.SearchShowResult.Result;
import edu.calpoly.idulkin.podcrust.rest.SearchShowResult.SearchShowResult;
import edu.calpoly.idulkin.podcrust.searchedList.SearchListActivity;
import edu.calpoly.idulkin.podcrust.searchedList.SearchListView;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SearchFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SearchFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TrendingFragment extends Fragment {

    private Audiosearch client;
    private List<TrendResult> trendResults;
    private TrendListView trendListView;

    private OnFragmentInteractionListener mListener;

    public TrendingFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static TrendingFragment newInstance() {
        TrendingFragment fragment = new TrendingFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        new Thread(new Runnable() {
            Audiosearch createClient() {
                Log.d("createClient", "trying...");
                try {
                    Audiosearch result = QueryExecutor.createClient();
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    } finally {
                        return createClient();
                    }
                }
            }

            List<TrendResult> getTrendingResult() {
                Log.d("getTrendShowResult", "trying...");
                try {
                    return client.getTrending().execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    } finally {
                        return getTrendingResult();
                    }
                }
            }


            @Override
            public void run() {
                client = createClient();
                Log.d("Search result", "client created");
                trendResults = getTrendingResult();
                Log.d("Search result", trendResults.toString());

                try {
                    for (TrendResult r : trendResults) {
                        Log.d("searchresult", r.getRelatedEpisodes().get(0).getShowTitle());
                    }

                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            trendListView = new TrendListView(getActivity().getBaseContext(), trendResults);
                            getActivity().setContentView(trendListView);
                        }
                    });

                } catch (Exception e) {
                    Log.d("trend list", e.getMessage());
                    this.run();
                }
            }

        }).start();

        return trendListView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
