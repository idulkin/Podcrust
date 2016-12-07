package audiosearch.service;

import java.util.List;

/*import aj.canvas.audiosearch.model.EpisodeQueryResult;
import aj.canvas.audiosearch.model.EpisodeResult;
import aj.canvas.audiosearch.model.TrendResult;*/
import audiosearch.model.EpisodeQueryResult;
import audiosearch.model.EpisodeResult;
import audiosearch.model.TrendResult;

import edu.calpoly.idulkin.podcrust.rest.Episode.Episode;
import edu.calpoly.idulkin.podcrust.rest.SearchShowResult.SearchShowResult;
import edu.calpoly.idulkin.podcrust.rest.Show.Show;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface AudiosearchService {

    @GET("tastemakers/episodes/{n}")
    Call<List<Object>> getTastemakersEpisodes(@Path("n") int n);

    @GET("/tastemakers/episodes/source/{taskmakerId}/{n}")
    Call<List<Object>> getEpisodesByTaskmaker(@Path("tastemakerId") String tastemakerId, @Path("n") int n);

    /*@GET("episodes/{id}")
    Call<EpisodeResult> getEpisode(@Path("id") long id, @Header("Authorization") String bearer);*/
    @GET("episodes/{id}")
    Call<Episode> getEpisode(@Path("id") long id, @Header("Authorization") String bearer);

    @GET("/search/shows/{query}")
    Call<List<Object>> search (@Path("query") String query);

    @GET("search/episodes/{query}")
    Call<EpisodeQueryResult> searchEpisodes(@Path("query") String query,@Header("Authorization") String bearer);

    //testing if i need bearer to search the episodes
    @GET("search/episodes/{query}")
    Call<EpisodeQueryResult> searchEpisodes(@Path("query") String query);

    @GET("episodes/{id}/related")
    Call<List<Object>> getRelatedEpisodes(@Path("id") long id);

    @GET("episodes/{id}/snippet/{timestamp}")
    Call<Object> getEpisodeMoment(@Path("id") long id, @Path("timestamp") long timestamp);

    @GET("trending")
    Call<List<TrendResult>> getTrending(@Header("Authorization") String bearer);

    @GET("tastemakers/shows/{n}")
    Call<List<Object>> getShowsbyTastemakers(@Path("n") int n);

    /*@GET("shows/{id}")
    Call<Object> getShow(@Path("id") long id);*/
    @GET("shows/{id}")
    Call<Show> getShow(@Path("id") long id, @Header("Authorization") String bearer);

    @GET("shows/{id}/related")
    Call<List<Object>> getRelatedShows(@Path("id") long id);

    @GET("search/shows/{query}")
    Call<SearchShowResult> getShowList(@Path("query") String query, @Header("Authorization") String bearer);

    @GET("people/{id}")
    Call<Object> getPerson(@Path("id") long id);

    @GET("search/people/{query}")
    Call<List<Object>> getPeople(@Path("query") String query);

    @GET("chart_daily")
    Call<List<Object>> getDailyChart();

}
