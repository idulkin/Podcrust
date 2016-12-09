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

import audiosearch.Audiosearch;
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
public class SearchFragment extends Fragment {

    private Audiosearch client;
    private SearchShowResult searchShowResult;
    private SearchListView searchListView;

    private OnFragmentInteractionListener mListener;

    public SearchFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment SearchFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
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
                    Audiosearch result = new Audiosearch()
                            .setApplicationId(getString(R.string.application_id))
                            .setSecret(getString(R.string.secret))
                            .build();
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

            SearchShowResult getSearchShowResult() {
                Log.d("getSearchShowResult", "trying...");
                try {
                    return client.searchShows("startup").execute().body();
                } catch (IOException e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                    } finally {
                        return getSearchShowResult();
                    }
                }
            }


        @Override
        public void run() {
            client = createClient();
            Log.d("Search result", "client created");
            searchShowResult = getSearchShowResult();
            Log.d("Search result", searchShowResult.toString());

            try {
                for (Result r : searchShowResult.getResults()) {
                    Log.d("searchresult", r.getTitle());
                }

                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        SearchListView.CharSequenceConsumer cb = new SearchListView.CharSequenceConsumer() {
                            @Override
                            public void cb(CharSequence s) {
                                Log.d("SearchListActivity", "text changed to " + s);
                                updateSearchList(s.toString());
                            }
                        };
                        searchListView = new SearchListView(getActivity().getApplicationContext(), searchShowResult, cb);
                        getActivity().setContentView(searchListView);
                    }
                });

            } catch (Exception e) {
                Log.d("searchlist", "failure to search");
                Log.d("searchlist", e.getMessage());
                this.run();
            }
        }

        }).start();

        return searchListView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
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

    private void updateSearchList(String query) {
        try {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        SearchShowResult searchShowResult2 = client.searchShows(query).execute().body();
                        searchShowResult.setResults(searchShowResult2.getResults());
                        searchListView.notifyDataSetChanged();
                        Log.d("SearchListActivity", "updateSearchList");
                    }
                    catch(Exception e) {
                        Log.d("SearchListActivity", e.toString());
                    }
                }
            }).start();


            /*runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setContentView(new SearchListView(thiz, searchShowResult, s -> {
                        Log.d("SearchListActivity", "text changed to " + s);
                        updateSearchList(s.toString());
                    }));
                }
            });*/
        }
        catch(Exception e) {
            Log.d("SearchListActivity", e.toString());
        }
    }
}
