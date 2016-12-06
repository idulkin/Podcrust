package edu.calpoly.idulkin.podcrust.searchedList;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import edu.calpoly.idulkin.podcrust.rest.SearchShowResult.SearchShowResult;
import trikita.anvil.BaseDSL;
import trikita.anvil.RenderableView;

import static trikita.anvil.BaseDSL.MATCH;
import static trikita.anvil.BaseDSL.onTextChanged;
import static trikita.anvil.BaseDSL.size;
import static trikita.anvil.DSL.adapter;
import static trikita.anvil.DSL.editText;
import static trikita.anvil.DSL.itemsCanFocus;
import static trikita.anvil.DSL.linearLayout;
import static trikita.anvil.DSL.listView;
import static trikita.anvil.DSL.onItemClick;
import static trikita.anvil.DSL.orientation;

/**
 * Created by Max on 12/1/2016.
 */

public class SearchListView extends RenderableView {

    private final static String TAG = "SearchListView";
    private final SearchedShowAdapter searchedShowAdapter;
    private final CharSequenceConsumer onTextChanged;
    private final SearchListView thiz;

    public interface CharSequenceConsumer {
        void cb(CharSequence s);
    }

    public SearchListView(Context c, SearchShowResult searchShowResult, CharSequenceConsumer onTextChanged) {
        super(c);
        searchedShowAdapter = new SearchedShowAdapter(searchShowResult);
        this.onTextChanged = onTextChanged;
        thiz = this;
    }

    public void notifyDataSetChanged() {
        this.searchedShowAdapter.notifyDataSetChanged();
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int pos, long id) {
            Log.d(TAG, "item clicked: " + searchedShowAdapter.getItem(pos).getTitle());
        }
    };

    @Override
    public void view() {
        searchedShowAdapter.notifyDataSetChanged();

        linearLayout(() -> {
            size(MATCH, MATCH);
            orientation(LinearLayout.VERTICAL);

            editText(() -> {
                onTextChanged(new BaseDSL.SimpleTextWatcher() {
                    @Override
                    public void onTextChanged(CharSequence s) {
                        thiz.onTextChanged.cb(s);
                    }
                });
            });

            listView(() -> {
                size(MATCH, MATCH);
                itemsCanFocus(true);
                onItemClick(onItemClickListener);
                adapter(searchedShowAdapter);
            });
        });
    }
}