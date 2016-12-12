package edu.calpoly.idulkin.podcrust;

import android.content.Context;
import android.graphics.Point;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.koushikdutta.ion.Ion;

import java.util.List;

import audiosearch.model.ImageUrls;
import audiosearch.model.TrendResult;
import edu.calpoly.idulkin.podcrust.R;
import edu.calpoly.idulkin.podcrust.rest.SearchShowResult.ImageFile;
import edu.calpoly.idulkin.podcrust.rest.SearchShowResult.Result;
import edu.calpoly.idulkin.podcrust.rest.SearchShowResult.SearchShowResult;
import trikita.anvil.Anvil;
import trikita.anvil.RenderableView;

import static trikita.anvil.BaseDSL.MATCH;
import static trikita.anvil.BaseDSL.WRAP;
import static trikita.anvil.BaseDSL.init;
import static trikita.anvil.BaseDSL.margin;
import static trikita.anvil.BaseDSL.padding;
import static trikita.anvil.BaseDSL.size;
import static trikita.anvil.DSL.imageView;
import static trikita.anvil.DSL.linearLayout;

/**
 * Created by Max on 12/1/2016.
 */

public class TrendResultAdapter extends BaseAdapter {

    private List<TrendResult> trendResults;
    public TrendResultAdapter(List<TrendResult> trendResults) {
        this.trendResults = trendResults;
    }

    @Override
    public int getCount() {
        return trendResults.size();
    }

    @Override
    public TrendResult getItem(int i) {
        return trendResults.get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getRelatedEpisodes().get(0).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return new RenderableView(viewGroup.getContext()) {
            @Override
            public void view() {

                linearLayout(() -> {
                    ImageUrls imageUrls = getCount() > i ? getItem(i).getRelatedEpisodes().get(0).getImageUrls(): null;
                    if (imageUrls.getFull() != null && !imageUrls.getFull().equals("")) {
                        imageView(() -> {
                            margin(0,0,0,0);
                            padding(0,0,0,0);

                            size(WRAP, WRAP);
                            init(() -> {
                                ImageView v = Anvil.currentView();
                                Ion.with(v)
                                        .error(R.drawable.ic_placeholder)
                                        .load(imageUrls.getFull());
                            });
                        });
                    }
                });
            }
        };
    }
}
