
package edu.calpoly.idulkin.podcrust.rest.Show;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
    "twitter",
    "facebook",
    "itunes_podcast",
    "personal"
})
public class WebProfiles {

    @JsonProperty("twitter")
    private Twitter twitter;
    @JsonProperty("facebook")
    private Facebook facebook;
    @JsonProperty("itunes_podcast")
    private ItunesPodcast itunesPodcast;
    @JsonProperty("personal")
    private Personal personal;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The twitter
     */
    @JsonProperty("twitter")
    public Twitter getTwitter() {
        return twitter;
    }

    /**
     * 
     * @param twitter
     *     The twitter
     */
    @JsonProperty("twitter")
    public void setTwitter(Twitter twitter) {
        this.twitter = twitter;
    }

    /**
     * 
     * @return
     *     The facebook
     */
    @JsonProperty("facebook")
    public Facebook getFacebook() {
        return facebook;
    }

    /**
     * 
     * @param facebook
     *     The facebook
     */
    @JsonProperty("facebook")
    public void setFacebook(Facebook facebook) {
        this.facebook = facebook;
    }

    /**
     * 
     * @return
     *     The itunesPodcast
     */
    @JsonProperty("itunes_podcast")
    public ItunesPodcast getItunesPodcast() {
        return itunesPodcast;
    }

    /**
     * 
     * @param itunesPodcast
     *     The itunes_podcast
     */
    @JsonProperty("itunes_podcast")
    public void setItunesPodcast(ItunesPodcast itunesPodcast) {
        this.itunesPodcast = itunesPodcast;
    }

    /**
     * 
     * @return
     *     The personal
     */
    @JsonProperty("personal")
    public Personal getPersonal() {
        return personal;
    }

    /**
     * 
     * @param personal
     *     The personal
     */
    @JsonProperty("personal")
    public void setPersonal(Personal personal) {
        this.personal = personal;
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
