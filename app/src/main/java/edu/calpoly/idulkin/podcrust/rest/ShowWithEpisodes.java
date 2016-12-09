package edu.calpoly.idulkin.podcrust.rest;

import java.util.ArrayList;

import edu.calpoly.idulkin.podcrust.rest.Episode.Episode;
import edu.calpoly.idulkin.podcrust.rest.Show.Show;

/**
 * Created by Max on 12/6/2016.
 */

public class ShowWithEpisodes {
    public Show show;
    public ArrayList<Episode> episodes;

    public ShowWithEpisodes(Show show, ArrayList<Episode> episodes) {
        this.show = show;
        this.episodes = episodes;
    }

    public Show getShow() {
        return show;
    }

    public ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public void setShow(Show show) {
        this.show = show;
    }

    public void setEpisodes(ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }
}
