package edu.calpoly.idulkin.podcrust;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import audiosearch.Audiosearch;
import audiosearch.exception.CredentialsNotFoundException;
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
public class SearchFragment extends Fragment {

    private Audiosearch client;
    private SearchShowResult searchShowResult;
    SearchListView searchListView;

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

        Toolbar toolbar = (Toolbar) getActivity().findViewById(R.id.toolbar);
        ((AppCompatActivity)getActivity()).setSupportActionBar(toolbar);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Thread t = new Thread(() -> {
            final Audiosearch client = createClient();
            searchShowResult = getSearchShowResult(client);
            SearchListView.CharSequenceConsumer cb = s -> {
                Log.d("SearchListActivity", "text changed to " + s);
                updateSearchList(s.toString());
            };
            searchListView = new SearchListView(getActivity().getBaseContext(), searchShowResult, cb);
        });
        t.start();
        try {
            t.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return searchListView;
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

    private void updateSearchList(String query) {
        new Thread(() -> {
            try {
                Audiosearch client = QueryExecutor.createClient();
                SearchShowResult searchShowResult2 = client.searchShows(query).execute().body();
                searchShowResult.setResults(searchShowResult2.getResults());
                searchListView.notifyDataSetChanged();
                Log.d("SearchListActivity", "updateSearchList");
            }
            catch(Exception e) {
                Log.d("SearchListActivity", e.toString());
                e.printStackTrace();
            }
        }).start();
    }

    private Audiosearch createClient() {
        Log.d("createClient", "trying...");
        try {
            return QueryExecutor.createClient();
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

    private SearchShowResult getSearchShowResult(Audiosearch client) {
        Log.d("getSearchShowResult", "trying...");
        try {
            return client.searchShows("startup").execute().body();
        } catch (IOException e) {
            e.printStackTrace();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e1) {
            } finally {
                return getSearchShowResult(client);
            }
        }
    }
}
