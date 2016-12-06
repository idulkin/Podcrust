package edu.calpoly.idulkin.podcrust.searchedList;

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

import edu.calpoly.idulkin.podcrust.R;
import edu.calpoly.idulkin.podcrust.rest.SearchShowResult.ImageFile;
import edu.calpoly.idulkin.podcrust.rest.SearchShowResult.Result;
import edu.calpoly.idulkin.podcrust.rest.SearchShowResult.SearchShowResult;
import trikita.anvil.Anvil;
import trikita.anvil.RenderableView;

import static trikita.anvil.BaseDSL.WRAP;
import static trikita.anvil.BaseDSL.init;
import static trikita.anvil.BaseDSL.size;
import static trikita.anvil.DSL.imageView;
import static trikita.anvil.DSL.linearLayout;

/**
 * Created by Max on 12/1/2016.
 */

public class SearchedShowAdapter extends BaseAdapter {

    private SearchShowResult searchShowResult;
    public SearchedShowAdapter(SearchShowResult searchShowResult) {
        this.searchShowResult = searchShowResult;
    }

    @Override
    public int getCount() {
        return searchShowResult.getResults().size();
    }

    @Override
    public Result getItem(int i) {
        return searchShowResult.getResults().get(i);
    }

    @Override
    public long getItemId(int i) {
        return getItem(i).getId();
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        return new RenderableView(viewGroup.getContext()) {
            @Override
            public void view() {
                linearLayout(new Anvil.Renderable() {
                    @Override
                    public void view() {
                        List<ImageFile> imageFiles = getCount() > i ? getItem(i).getImageFiles(): null;

                        if (imageFiles != null && imageFiles.size() > 0) {
                            size(WRAP, WRAP);
                            imageView(() -> {
                                WindowManager wm = (WindowManager) viewGroup.getContext().getSystemService(Context.WINDOW_SERVICE);
                                Display display = wm.getDefaultDisplay();
                                Point size = new Point();
                                display.getSize(size);
                                int width = size.x;
                                int height = size.y;
                                final int imageSize = width;

                                size(imageSize, imageSize);
                                init(() -> {
                                    ImageView v = Anvil.currentView();
                                    v.setMaxHeight(imageSize);
                                    v.setMaxHeight(imageSize);
                                    if (imageFiles != null && imageFiles.size() > 0) {
                                        Ion.with(v)
                                            .error(R.drawable.ic_placeholder)
                                            .load(imageFiles.get(0).getOriginalFileUrl());
                                    }
                                });
                            });
                        }
                    }
                });
            }
        };
    }
}
