package edu.calpoly.idulkin.podcrust;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;

import java.util.List;

import audiosearch.model.TrendResult;
import trikita.anvil.RenderableView;

import static trikita.anvil.BaseDSL.MATCH;
import static trikita.anvil.BaseDSL.margin;
import static trikita.anvil.BaseDSL.padding;
import static trikita.anvil.BaseDSL.size;
import static trikita.anvil.DSL.adapter;
import static trikita.anvil.DSL.columnWidth;
import static trikita.anvil.DSL.gridView;
import static trikita.anvil.DSL.horizontalSpacing;
import static trikita.anvil.DSL.itemsCanFocus;
import static trikita.anvil.DSL.linearLayout;
import static trikita.anvil.DSL.numColumns;
import static trikita.anvil.DSL.onItemClick;
import static trikita.anvil.DSL.orientation;

/**
 * Created by Max on 12/1/2016.
 */

public class TrendListView extends RenderableView {

    private final static String TAG = "TrendListView";
    private final TrendResultAdapter trendResultAdapter;
    private final TrendListView thiz;
    private Context context;

    public TrendListView(Context c, List<TrendResult> trendResults) {
        super(c);
        this.trendResultAdapter = new TrendResultAdapter(trendResults);
        context = c;
        thiz = this;
    }

    AdapterView.OnItemClickListener onItemClickListener = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView parent, View v, int pos, long id) {
            Log.d(TAG, "item clicked: "
                    + trendResultAdapter.getItem(pos).getRelatedEpisodes().get(0).getShowTitle()
                    + "item id " + trendResultAdapter.getItemId(pos));
            Context context = thiz.getContext();
            Intent intent = new Intent(context, EpisodeListActivity.class);
            intent.putExtra("SHOWID", (long)trendResultAdapter.getItem(pos).getRelatedEpisodes().get(0).getShowId());
            intent.putExtra("TITLE", trendResultAdapter.getItem(pos).getRelatedEpisodes().get(0).getShowTitle());
            intent.putExtra("IMAGEURL", trendResultAdapter.getItem(pos).getRelatedEpisodes().get(0).getImageUrls().getFull());
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            thiz.getContext().startActivity(intent);
        }
    };

    @Override
    public void view() {
        trendResultAdapter.notifyDataSetChanged();

        /*DisplayMetrics displayMetrics = new DisplayMetrics();
        (/*(Activity) getContext())
                .getWindowManager()
                .getDefaultDisplay()
                .getMetrics(displayMetrics);
        int screenWidth = displayMetrics.widthPixels;*/

        linearLayout(() -> {
            size(MATCH, MATCH);
            orientation(LinearLayout.VERTICAL);

            gridView(() -> {
                size(MATCH, MATCH);
                itemsCanFocus(true);
                margin(0,0,0,0 + 0);
                padding(0,0,0,0);
                onItemClick(onItemClickListener);
                adapter(trendResultAdapter);
                //horizontalSpacing(0);
                numColumns(3);
                //columnWidth(screenWidth / 3);

            });
        });
    }
}