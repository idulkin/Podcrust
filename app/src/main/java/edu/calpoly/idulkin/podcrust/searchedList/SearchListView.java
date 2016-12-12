package edu.calpoly.idulkin.podcrust.searchedList;

import android.content.Context;
import android.content.Intent;
import android.icu.text.MeasureFormat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import edu.calpoly.idulkin.podcrust.EpisodeListActivity;
import edu.calpoly.idulkin.podcrust.rest.SearchShowResult.SearchShowResult;
import trikita.anvil.BaseDSL;
import trikita.anvil.RenderableView;

import static trikita.anvil.BaseDSL.CENTER;
import static trikita.anvil.BaseDSL.MATCH;
import static trikita.anvil.BaseDSL.WRAP;
import static trikita.anvil.BaseDSL.dip;
import static trikita.anvil.BaseDSL.onTextChanged;
import static trikita.anvil.BaseDSL.size;
import static trikita.anvil.DSL.adapter;
import static trikita.anvil.DSL.columnWidth;
import static trikita.anvil.DSL.editText;
import static trikita.anvil.DSL.gravity;
import static trikita.anvil.DSL.gridView;
import static trikita.anvil.DSL.hint;
import static trikita.anvil.DSL.horizontalSpacing;
import static trikita.anvil.DSL.itemsCanFocus;
import static trikita.anvil.DSL.linearLayout;
import static trikita.anvil.DSL.listView;
import static trikita.anvil.DSL.numColumns;
import static trikita.anvil.DSL.onItemClick;
import static trikita.anvil.DSL.orientation;
import static trikita.anvil.DSL.stretchAllColumns;
import static trikita.anvil.DSL.stretchMode;
import static trikita.anvil.DSL.verticalSpacing;

/**
 * Created by Max on 12/1/2016.
 */

public class SearchListView extends RenderableView {

    private final static String TAG = "SearchListView";
    private final SearchedShowAdapter searchedShowAdapter;
    private final CharSequenceConsumer onTextChanged;
    private final SearchListView thiz;
    private Context context;

    public interface CharSequenceConsumer {
        void cb(CharSequence s);
    }

    public SearchListView(Context c, SearchShowResult searchShowResult, CharSequenceConsumer onTextChanged) {
        super(c);
        searchedShowAdapter = new SearchedShowAdapter(searchShowResult);
        this.onTextChanged = onTextChanged;
        context = c;
        thiz = this;
    }

    public void notifyDataSetChanged() {
        this.searchedShowAdapter.notifyDataSetChanged();
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int pos, long id) {
            Log.d(TAG, "item clicked: " + searchedShowAdapter.getItem(pos).getTitle() + "item id " + searchedShowAdapter.getItemId(pos));
            Context context = thiz.getContext();
            Intent intent = new Intent(context, EpisodeListActivity.class);
            intent.putExtra("SHOWID", searchedShowAdapter.getItemId(pos));
            intent.putExtra("TITLE", searchedShowAdapter.getItem(pos).getTitle());
            intent.putExtra("IMAGEURL", searchedShowAdapter.getItem(pos).getImageFiles().get(0).getOriginalFileUrl());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            thiz.getContext().startActivity(intent);
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
                hint("Search for podcasts...");
            });

            int asdf = 1324;

            gridView(() -> {
                size(MATCH, MATCH);
                itemsCanFocus(true);
                onItemClick(onItemClickListener);
                adapter(searchedShowAdapter);
                //columnWidth(dip(90));
                //verticalSpacing(dip(10));
                //horizontalSpacing(dip(10));
                horizontalSpacing(0);
                numColumns(3);
                stretchAllColumns(true);

                //gravity(CENTER);
                /*android:columnWidth="90dp"
                android:numColumns="auto_fit"
                android:verticalSpacing="10dp"
                android:horizontalSpacing="10dp"*/
            });
        });
    }
}