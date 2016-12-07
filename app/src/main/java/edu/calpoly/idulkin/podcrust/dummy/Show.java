package edu.calpoly.idulkin.podcrust.dummy;
// This is a hard coded test to show results
// Will be only implemented in Master-Detail View

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import audiosearch.Audiosearch;
import edu.calpoly.idulkin.podcrust.rest.Episode.Episode;
import edu.calpoly.idulkin.podcrust.rest.QueryExecutor;

/**
 * Created by Jonathan Chianglin on 12/1/2016.
 */

public class Show {
    private String title;
    private String image;
    private dummyEpisode[] list = new dummyEpisode[10];
    private ArrayList<dummyEpisode> arrayList = new ArrayList<>();
    private Audiosearch client;
    public Show() {
        this.title = "";
        this.image = "";
//        populateList();
    }

    public Show(Audiosearch client, long show_id) throws IOException {
        this.client = client;
        edu.calpoly.idulkin.podcrust.rest.Show.Show temp = QueryExecutor.getShow(client, show_id);
        this.title = temp.getTitle();
        this.image = temp.getImageFiles().get(0).getUrl().getFull();

        List<Integer> episodeIdList = temp.getEpisodeIds();
        populateList(episodeIdList);
    }

    public void populateList(List<Integer> episodeIdList) {
//        for (int i = 0; i < episodeIdList.size(); i++) {
//            try {
//                Episode episode = QueryExecutor.getEpisode(client, episodeIdList.get(i));
//                String mp3 = episode.getAudioFiles().get(0).getUrl();
//                arrayList.add(new dummyEpisode(episode.getTitle(), episode.getDescription(), mp3));
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }
        for (int i = 0; i < list.length; i++) {
            try {
                Episode episode = QueryExecutor.getEpisode(client, episodeIdList.get(i));
                String mp3 = episode.getAudioFiles().get(0).getUrl();
                list[i] = new dummyEpisode(episode.getTitle(), episode.getDescription(), mp3);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public dummyEpisode[] getList() {
        return list;
    }

    public void setList(dummyEpisode[] list) {
        this.list = list;
    }

    public  ArrayList<dummyEpisode> getArrayList(){
        return arrayList;
    }

    public void setArrayList(ArrayList<dummyEpisode> arrayList) {
        this.arrayList = arrayList;
    }

    public class dummyEpisode {
        private String episodeTitle;
        private String description;
        private String mp3;

        public dummyEpisode(String episodeTitle, String description, String mp3) {
            this.episodeTitle = episodeTitle;
            this.description = description;
            this.mp3 = mp3;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getMp3() {
            return mp3;
        }

        public void setMp3(String mp3) {
            this.mp3 = mp3;
        }

        public String getEpisodeTitle() {
            return episodeTitle;
        }

        public void setEpisodeTitle(String episodeTitle) {
            this.episodeTitle = episodeTitle;
        }
    }

}
