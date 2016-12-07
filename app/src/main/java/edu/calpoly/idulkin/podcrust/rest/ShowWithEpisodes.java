package edu.calpoly.idulkin.podcrust.rest;

import android.app.DownloadManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import audiosearch.Audiosearch;
import audiosearch.exception.CredentialsNotFoundException;
import edu.calpoly.idulkin.podcrust.rest.Episode.Episode;
import edu.calpoly.idulkin.podcrust.rest.Show.Show;

/**
 * Created by Max on 12/6/2016.
 */

public class ShowWithEpisodes {
    public final Show show;
    public final ArrayList<Episode> episodes;

    public ShowWithEpisodes(Show show, ArrayList<Episode> episodes) {
        this.show = show;
        this.episodes = episodes;
    }
}
