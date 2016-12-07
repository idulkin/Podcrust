
package edu.calpoly.idulkin.podcrust.rest.Episode;

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
    "date_created",
    "identifier",
    "digital_location",
    "physical_location",
    "duration",
    "tags",
    "updated_at",
    "itunes_episode",
    "date_added",
    "show_id",
    "show_title",
    "audio_files",
    "image_files",
    "rss_url",
    "extra",
    "urls",
    "categories",
    "highlights",
    "entities"
})
public class Episode {

    @JsonProperty("id")
    private Integer id;
    @JsonProperty("title")
    private String title;
    @JsonProperty("description")
    private String description;
    @JsonProperty("date_created")
    private String dateCreated;
    @JsonProperty("identifier")
    private String identifier;
    @JsonProperty("digital_location")
    private String digitalLocation;
    @JsonProperty("physical_location")
    private String physicalLocation;
    @JsonProperty("duration")
    private Integer duration;
    @JsonProperty("tags")
    private List<Object> tags = null;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("itunes_episode")
    private Integer itunesEpisode;
    @JsonProperty("date_added")
    private String dateAdded;
    @JsonProperty("show_id")
    private Integer showId;
    @JsonProperty("show_title")
    private String showTitle;
    @JsonProperty("audio_files")
    private List<AudioFile> audioFiles = null;
    @JsonProperty("image_files")
    private List<Object> imageFiles = null;
    @JsonProperty("rss_url")
    private String rssUrl;
    @JsonProperty("extra")
    private Extra extra;
    @JsonProperty("urls")
    private Urls urls;
    @JsonProperty("categories")
    private List<Category> categories = null;
    @JsonProperty("highlights")
    private Highlights highlights;
    @JsonProperty("entities")
    private List<Entity> entities = null;
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
     *     The dateCreated
     */
    @JsonProperty("date_created")
    public String getDateCreated() {
        return dateCreated;
    }

    /**
     * 
     * @param dateCreated
     *     The date_created
     */
    @JsonProperty("date_created")
    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    /**
     * 
     * @return
     *     The identifier
     */
    @JsonProperty("identifier")
    public String getIdentifier() {
        return identifier;
    }

    /**
     * 
     * @param identifier
     *     The identifier
     */
    @JsonProperty("identifier")
    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    /**
     * 
     * @return
     *     The digitalLocation
     */
    @JsonProperty("digital_location")
    public String getDigitalLocation() {
        return digitalLocation;
    }

    /**
     * 
     * @param digitalLocation
     *     The digital_location
     */
    @JsonProperty("digital_location")
    public void setDigitalLocation(String digitalLocation) {
        this.digitalLocation = digitalLocation;
    }

    /**
     * 
     * @return
     *     The physicalLocation
     */
    @JsonProperty("physical_location")
    public String getPhysicalLocation() {
        return physicalLocation;
    }

    /**
     * 
     * @param physicalLocation
     *     The physical_location
     */
    @JsonProperty("physical_location")
    public void setPhysicalLocation(String physicalLocation) {
        this.physicalLocation = physicalLocation;
    }

    /**
     * 
     * @return
     *     The duration
     */
    @JsonProperty("duration")
    public Integer getDuration() {
        return duration;
    }

    /**
     * 
     * @param duration
     *     The duration
     */
    @JsonProperty("duration")
    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    /**
     * 
     * @return
     *     The tags
     */
    @JsonProperty("tags")
    public List<Object> getTags() {
        return tags;
    }

    /**
     * 
     * @param tags
     *     The tags
     */
    @JsonProperty("tags")
    public void setTags(List<Object> tags) {
        this.tags = tags;
    }

    /**
     * 
     * @return
     *     The updatedAt
     */
    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 
     * @param updatedAt
     *     The updated_at
     */
    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * 
     * @return
     *     The itunesEpisode
     */
    @JsonProperty("itunes_episode")
    public Integer getItunesEpisode() {
        return itunesEpisode;
    }

    /**
     * 
     * @param itunesEpisode
     *     The itunes_episode
     */
    @JsonProperty("itunes_episode")
    public void setItunesEpisode(Integer itunesEpisode) {
        this.itunesEpisode = itunesEpisode;
    }

    /**
     * 
     * @return
     *     The dateAdded
     */
    @JsonProperty("date_added")
    public String getDateAdded() {
        return dateAdded;
    }

    /**
     * 
     * @param dateAdded
     *     The date_added
     */
    @JsonProperty("date_added")
    public void setDateAdded(String dateAdded) {
        this.dateAdded = dateAdded;
    }

    /**
     * 
     * @return
     *     The showId
     */
    @JsonProperty("show_id")
    public Integer getShowId() {
        return showId;
    }

    /**
     * 
     * @param showId
     *     The show_id
     */
    @JsonProperty("show_id")
    public void setShowId(Integer showId) {
        this.showId = showId;
    }

    /**
     * 
     * @return
     *     The showTitle
     */
    @JsonProperty("show_title")
    public String getShowTitle() {
        return showTitle;
    }

    /**
     * 
     * @param showTitle
     *     The show_title
     */
    @JsonProperty("show_title")
    public void setShowTitle(String showTitle) {
        this.showTitle = showTitle;
    }

    /**
     * 
     * @return
     *     The audioFiles
     */
    @JsonProperty("audio_files")
    public List<AudioFile> getAudioFiles() {
        return audioFiles;
    }

    /**
     * 
     * @param audioFiles
     *     The audio_files
     */
    @JsonProperty("audio_files")
    public void setAudioFiles(List<AudioFile> audioFiles) {
        this.audioFiles = audioFiles;
    }

    /**
     * 
     * @return
     *     The imageFiles
     */
    @JsonProperty("image_files")
    public List<Object> getImageFiles() {
        return imageFiles;
    }

    /**
     * 
     * @param imageFiles
     *     The image_files
     */
    @JsonProperty("image_files")
    public void setImageFiles(List<Object> imageFiles) {
        this.imageFiles = imageFiles;
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
     *     The extra
     */
    @JsonProperty("extra")
    public Extra getExtra() {
        return extra;
    }

    /**
     * 
     * @param extra
     *     The extra
     */
    @JsonProperty("extra")
    public void setExtra(Extra extra) {
        this.extra = extra;
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
     *     The categories
     */
    @JsonProperty("categories")
    public List<Category> getCategories() {
        return categories;
    }

    /**
     * 
     * @param categories
     *     The categories
     */
    @JsonProperty("categories")
    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    /**
     * 
     * @return
     *     The highlights
     */
    @JsonProperty("highlights")
    public Highlights getHighlights() {
        return highlights;
    }

    /**
     * 
     * @param highlights
     *     The highlights
     */
    @JsonProperty("highlights")
    public void setHighlights(Highlights highlights) {
        this.highlights = highlights;
    }

    /**
     * 
     * @return
     *     The entities
     */
    @JsonProperty("entities")
    public List<Entity> getEntities() {
        return entities;
    }

    /**
     * 
     * @param entities
     *     The entities
     */
    @JsonProperty("entities")
    public void setEntities(List<Entity> entities) {
        this.entities = entities;
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
