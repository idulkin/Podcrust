package edu.calpoly.idulkin.podcrust.searchedList;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.io.IOException;

import audiosearch.Audiosearch;
import edu.calpoly.idulkin.podcrust.rest.SearchShowResult.Result;
import edu.calpoly.idulkin.podcrust.rest.SearchShowResult.SearchShowResult;

/**
 * Created by Max on 12/1/2016.
 */

public class SearchListActivity extends AppCompatActivity {
    private Audiosearch client;
    private SearchShowResult searchShowResult;
    private SearchListView searchListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(new SearchListView(this, ));

        final String callbackUrl = "urn:ietf:wg:oauth:2.0:oob";
        final String applicationId = "c2b235f2620e362157a40aec609e737fe5a2547784933e00201ff90358e092c5";
        final String secret = "bee75fbb20ce6b45b64113b44208d12aeca02121fee8ea40f1bd9f44b491ba1c";
        final String authorizationCode = "ad2311b2860d224f89c32b7dfd4cb99550ba358aef412fae9ad11b52957a8930";
        final SearchListActivity thiz = this;

        new Thread(new Runnable() {
            Audiosearch createClient() {
                Log.d("createClient", "trying...");
                try {
                    Audiosearch result = new Audiosearch()
                            .setApplicationId(applicationId)
                            .setSecret(secret)
                            .build();
                    return result;
                } catch (Exception e) {
                    e.printStackTrace();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) { }
                    finally {
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
                    } catch (InterruptedException e1) { }
                    finally {
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

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            SearchListView.CharSequenceConsumer cb = new SearchListView.CharSequenceConsumer() {
                                @Override
                                public void cb(CharSequence s) {
                                    Log.d("SearchListActivity", "text changed to " + s);
                                    updateSearchList(s.toString());
                                }
                            };
                            searchListView = new SearchListView(thiz, searchShowResult, cb);
                            setContentView(searchListView);
                        }
                    });

                } catch (Exception e) {
                    Log.d("searchlist", "failure to search");
                    Log.d("searchlist", e.getMessage());
                    this.run();
                }
            }
        }).start();

        /*new Thread(() -> {
            try {
                Audiosearch client = new Audiosearch()
                        .setApplicationId(applicationId)
                        .setSecret(secret)
                        .build();

                SearchShowResult searchShowResult = client.searchShows("startup").execute().body();
                Log.d("searchresult", searchShowResult.toString());

                for (Result r : searchShowResult.getResults()) {
                    Log.d("searchresult", r.getTitle());
                }

                runOnUiThread(() -> {
                    setContentView(new SearchListView(this, searchShowResult));
                });
            }
            catch (Exception e) {
                Log.d("searchlist", "failure to search");
                Log.d("searchlist", e.getMessage());
            }
        }).start();*/
    }

    private void updateSearchList(String query) {
        try {
            SearchListActivity thiz = this;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        SearchShowResult searchShowResult2 = client.searchShows(query).execute().body();
                        thiz.searchShowResult.setResults(searchShowResult2.getResults());
                        thiz.searchListView.notifyDataSetChanged();
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
