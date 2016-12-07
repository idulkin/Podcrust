
package edu.calpoly.idulkin.podcrust.rest.Episode;

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
    "skip_transcript"
})
public class Extra {

    @JsonProperty("skip_transcript")
    private String skipTranscript;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The skipTranscript
     */
    @JsonProperty("skip_transcript")
    public String getSkipTranscript() {
        return skipTranscript;
    }

    /**
     * 
     * @param skipTranscript
     *     The skip_transcript
     */
    @JsonProperty("skip_transcript")
    public void setSkipTranscript(String skipTranscript) {
        this.skipTranscript = skipTranscript;
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
