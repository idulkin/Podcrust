
package edu.calpoly.idulkin.podcrust.rest.Show;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "id",
    "title",
    "description",
    "network",
    "itunes_id",
    "categories",
    "image_files",
    "number_of_episodes",
    "episode_ids",
    "urls",
    "rss_url",
    "sc_feed",
    "web_profiles"
})
public class Show {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("network")
    private String network;
    @JsonProperty("itunes_id")
    private Integer itunesId;
    @JsonProperty("categories")
    private List<String> categories = null;
    @JsonProperty("image_files")
    private List<ImageFile> imageFiles = null;
    @JsonProperty("number_of_episodes")
    private Integer numberOfEpisodes;
    @JsonProperty("episode_ids")
    private List<Integer> episodeIds = null;
    @JsonProperty("urls")
    private Urls urls;
    @JsonProperty("rss_url")
    private String rssUrl;
    @JsonProperty("sc_feed")
    private String scFeed;
    @JsonProperty("web_profiles")
    private WebProfiles webProfiles;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The id
     */
    @JsonProperty("id")
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    @JsonProperty("id")
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The title
     */
    @JsonProperty("title")
    public String getTitle() {
        return title;
    }

    /**
     * 
     * @param title
     *     The title
     */
    @JsonProperty("title")
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * 
     * @return
     *     The description
     */
    @JsonProperty("description")
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    @JsonProperty("description")
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The network
     */
    @JsonProperty("network")
    public String getNetwork() {
        return network;
    }

    /**
     * 
     * @param network
     *     The network
     */
    @JsonProperty("network")
    public void setNetwork(String network) {
        this.network = network;
    }

    /**
     * 
     * @return
     *     The itunesId
     */
    @JsonProperty("itunes_id")
    public Integer getItunesId() {
        return itunesId;
    }

    /**
     * 
     * @param itunesId
     *     The itunes_id
     */
    @JsonProperty("itunes_id")
    public void setItunesId(Integer itunesId) {
        this.itunesId = itunesId;
    }

    /**
     * 
     * @return
     *     The categories
     */
    @JsonProperty("categories")
    public List<String> getCategories() {
        return categories;
    }

    /**
     * 
     * @param categories
     *     The categories
     */
    @JsonProperty("categories")
    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    /**
     * 
     * @return
     *     The imageFiles
     */
    @JsonProperty("image_files")
    public List<ImageFile> getImageFiles() {
        return imageFiles;
    }

    /**
     * 
     * @param imageFiles
     *     The image_files
     */
    @JsonProperty("image_files")
    public void setImageFiles(List<ImageFile> imageFiles) {
        this.imageFiles = imageFiles;
    }

    /**
     * 
     * @return
     *     The numberOfEpisodes
     */
    @JsonProperty("number_of_episodes")
    public Integer getNumberOfEpisodes() {
        return numberOfEpisodes;
    }

    /**
     * 
     * @param numberOfEpisodes
     *     The number_of_episodes
     */
    @JsonProperty("number_of_episodes")
    public void setNumberOfEpisodes(Integer numberOfEpisodes) {
        this.numberOfEpisodes = numberOfEpisodes;
    }

    /**
     * 
     * @return
     *     The episodeIds
     */
    @JsonProperty("episode_ids")
    public List<Integer> getEpisodeIds() {
        return episodeIds;
    }

    /**
     * 
     * @param episodeIds
     *     The episode_ids
     */
    @JsonProperty("episode_ids")
    public void setEpisodeIds(List<Integer> episodeIds) {
        this.episodeIds = episodeIds;
    }

    /**
     * 
     * @return
     *     The urls
     */
    @JsonProperty("urls")
    public Urls getUrls() {
        return urls;
    }

    /**
     * 
     * @param urls
     *     The urls
     */
    @JsonProperty("urls")
    public void setUrls(Urls urls) {
        this.urls = urls;
    }

    /**
     * 
     * @return
     *     The rssUrl
     */
    @JsonProperty("rss_url")
    public String getRssUrl() {
        return rssUrl;
    }

    /**
     * 
     * @param rssUrl
     *     The rss_url
     */
    @JsonProperty("rss_url")
    public void setRssUrl(String rssUrl) {
        this.rssUrl = rssUrl;
    }

    /**
     * 
     * @return
     *     The scFeed
     */
    @JsonProperty("sc_feed")
    public String getScFeed() {
        return scFeed;
    }

    /**
     * 
     * @param scFeed
     *     The sc_feed
     */
    @JsonProperty("sc_feed")
    public void setScFeed(String scFeed) {
        this.scFeed = scFeed;
    }

    /**
     * 
     * @return
     *     The webProfiles
     */
    @JsonProperty("web_profiles")
    public WebProfiles getWebProfiles() {
        return webProfiles;
    }

    /**
     * 
     * @param webProfiles
     *     The web_profiles
     */
    @JsonProperty("web_profiles")
    public void setWebProfiles(WebProfiles webProfiles) {
        this.webProfiles = webProfiles;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
